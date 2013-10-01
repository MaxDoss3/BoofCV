/*
 * Copyright (c) 2011-2013, Peter Abeles. All Rights Reserved.
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

package boofcv.alg.sfm.robust;

import boofcv.struct.geo.GeoModelRefine;
import org.ddogleg.fitting.modelset.ModelFitter;

import java.util.List;

/**
 * Wrapper around {@link GeoModelRefine} for {@link ModelFitter}
 *
 * @author Peter Abeles
 */
public class GeoModelRefineToModelFitter<Model,Point> implements ModelFitter<Model,Point> {

	GeoModelRefine<Model,Point> alg;

	public GeoModelRefineToModelFitter(GeoModelRefine<Model, Point> alg) {
		this.alg = alg;
	}

	@Override
	public boolean fitModel(List<Point> dataSet, Model initial, Model found) {
		return alg.process(initial,dataSet,found);
	}
}
