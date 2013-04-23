/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.gl2jni;


public class GL2JNILib {

     static {
         System.loadLibrary("gl2jni");
     }

     public static native void step();
	 public static native void fire(int x, int y);
	 public static native int[] getSpaceManPos();
	 public static native void addBody(int xPos, int yPos, int r, int m);
	 public static native void addEndMoon(int xPos, int yPos, int r, int m);
	 public static native void addSpaceMan(int xPos, int yPos, int r, int m);
	 public static native void reset();
}
