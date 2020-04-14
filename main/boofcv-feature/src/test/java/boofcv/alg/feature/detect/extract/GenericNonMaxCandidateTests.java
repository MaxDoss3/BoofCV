/*
 * Copyright (c) 2011-2020, Peter Abeles. All Rights Reserved.
 *
 * This file is part of BoofCV (http://boofcv.org).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package boofcv.alg.feature.detect.extract;

import boofcv.struct.QueueCorner;
import boofcv.struct.image.GrayF32;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Peter Abeles
 */
public abstract class GenericNonMaxCandidateTests extends GenericNonMaxTests {

	QueueCorner candidatesMin = new QueueCorner();
	QueueCorner candidatesMax  = new QueueCorner();

	NonMaxCandidate nonmax;

	protected GenericNonMaxCandidateTests(boolean strict, boolean canDetectMin, boolean canDetectMax,
										  NonMaxCandidate.Search search) {
		super(strict, canDetectMin, canDetectMax);
		this.nonmax = new NonMaxCandidate(search);
	}

	protected GenericNonMaxCandidateTests(boolean strict, boolean canDetectMin, boolean canDetectMax,
										  NonMaxCandidate.Search search , boolean concurrent ) {
		super(strict, canDetectMin, canDetectMax);
		this.nonmax = concurrent ? new NonMaxCandidate_MT(search) : new NonMaxCandidate(search);
	}

	@Override
	public void findPeaks(GrayF32 intensity, float threshold, int radius, int border,
						  QueueCorner foundMinimum, QueueCorner foundMaximum) {
		allCandidates(intensity.width,intensity.height);

		findMaximums(intensity,threshold,radius,border,candidatesMin,candidatesMax,foundMinimum,foundMaximum);
	}

	public void findMaximums(GrayF32 intensity, float threshold, int radius, int border,
									  QueueCorner candidatesMin , QueueCorner candidatesMax,
									  QueueCorner foundMinimum, QueueCorner foundMaximum)
	{
		nonmax.radius = radius;
		nonmax.ignoreBorder = border;
		nonmax.thresholdMin = -threshold;
		nonmax.thresholdMax = threshold;

		nonmax.process(intensity, candidatesMin, candidatesMax, foundMinimum, foundMaximum);
	}

	public void allCandidates( int w,  int h ) {
		candidatesMin.reset();
		candidatesMax.reset();

		for( int y = 0; y < h; y++ ) {
			for( int x = 0; x < w; x++ ) {
				candidatesMin.append(x,y);
				candidatesMax.append(x,y);
			}
		}
	}

	@Override
	public void allStandard() {
		super.allStandard();
		testNullCandidate();
	}

	/**
	 * See if null candidates are correctly handled and don't blow up
	 */
	@Test
	public void testNullCandidate() {
		reset();

		intensity.set(3, 5, 30);

		intensity.set(2, 5, -30);

		candidatesMin.append(2,5);
		candidatesMax.append(3,5);

		foundMinimum.reset();foundMaximum.reset();
		findMaximums(intensity, 0.5f, 1, 0, null, candidatesMax, foundMinimum, foundMaximum);

		assertEquals(0,foundMinimum.size);
		assertEquals(1,foundMaximum.size);

		foundMinimum.reset();foundMaximum.reset();
		findMaximums(intensity, 0.5f, 1, 0, candidatesMin, null, foundMinimum, foundMaximum);

		assertEquals(1,foundMinimum.size);
		assertEquals(0,foundMaximum.size);

	}
}
