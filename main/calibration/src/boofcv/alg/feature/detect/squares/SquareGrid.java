/*
 * Copyright (c) 2011-2015, Peter Abeles. All Rights Reserved.
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

package boofcv.alg.feature.detect.squares;

import java.util.List;

/**
 * Data structure which describes a set of {@link SquareNode} as a grid.  Must be a complete grid with no
 * missing elements.  CW or CCW orientation is not specified.
 *
 * @author Peter Abeles
 */
public class SquareGrid {
	public List<SquareNode> nodes;
	public int columns;
	public int rows;

	public SquareNode get( int row , int col ) {
		if( row < 0 )
			row = rows + row;
		if( col < 0 )
			col = columns + col;
		return nodes.get( row*columns + col );
	}

	public SquareNode getCornerByIndex( int index ) {
		switch( index ) {
			case 0: return get(0,0);
			case 1: return get(0,-1);
			case 2: return get(-1,-1);
			case 3: return get(-1,0);
			default: throw new RuntimeException("BUG!");
		}
	}

	public int getCornerIndex( SquareNode node ) {
		int index = nodes.indexOf(node);

		int x = index%columns;
		int y = index/columns;

		if( x == 0 && y == 0 ) {
			return 0;
		} else if( x == columns-1 && y == 0 ) {
			return 1;
		} else if( x == columns-1 && y == rows-1 ) {
			return 2;
		} else if( x == 0 && y == rows-1 ) {
			return 3;
		} else {
			throw new RuntimeException("Not corner!");
		}
	}
}
