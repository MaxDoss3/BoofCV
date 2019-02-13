/*
 * Copyright (c) 2011-2019, Peter Abeles. All Rights Reserved.
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

package boofcv.alg.distort;

import boofcv.struct.distort.Point2Transform2Model_F32;
import georegression.struct.homography.Homography2D_F32;
import georegression.struct.homography.UtilHomography_F32;
import georegression.struct.point.Point2D_F32;
import georegression.transform.homography.HomographyPointOps_F32;
import org.ejml.data.FMatrix;
import org.ejml.data.FMatrixRMaj;


/**
 * {@link Point2Transform2Model_F32} using {@link Homography2D_F32}.
 *
 * @author Peter Abeles
 */
public class PointTransformHomography_F32 implements Point2Transform2Model_F32<Homography2D_F32> {

	Homography2D_F32 homo = new Homography2D_F32();

	public PointTransformHomography_F32() {
	}

	public PointTransformHomography_F32(FMatrixRMaj homo) {
		UtilHomography_F32.convert(homo, this.homo);
	}

	public PointTransformHomography_F32(Homography2D_F32 homo) {
		set(homo);
	}

	public void set(FMatrix transform ) {
		this.homo.set(transform);
	}

	@Override
	public void compute(float x, float y, Point2D_F32 out) {
		HomographyPointOps_F32.transform(homo, x, y, out);
	}

	@Override
	public void setModel(Homography2D_F32 o) {
		homo.set(o);
	}

	@Override
	public Homography2D_F32 getModel() {
		return homo;
	}

	@Override
	public Homography2D_F32 newInstanceModel() {
		return new Homography2D_F32();
	}

	@Override
	public boolean isThreadSafe() {
		return true;
	}
}
