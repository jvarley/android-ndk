# Copyright (C) 2009 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#




#from chipmunk

# This makefile will let the Android NDK build a
# libchipmunk.so to go with your 2D Android game.
 
# download the Chipmunk sources and do
# $ cd <chipmunk-physics>
# $ git checkout Chipmunk-6.0.3 # if you downloaded from source
# $ mkdir -p modules/chipmunk && cd modules/chipmunk
# $ cat >Android.mk <<EOF
 
include $(CLEAR_VARS)
LOCAL_MODULE := chipmunk
# using floats for convenience: GL_DOUBLE is not available on opengl es
# allowing private access for chipmunk-draw library
LOCAL_CFLAGS := -std=c99 -DCP_USE_DOUBLES=0 -DCP_ALLOW_PRIVATE_ACCESS=1
LOCAL_EXPORT_CFLAGS := -DCP_USE_DOUBLES=0 -DCP_ALLOW_PRIVATE_ACCESS=1
LOCAL_C_INCLUDES := chipmunk/include/chipmunk
LOCAL_EXPORT_C_INCLUDES := chipmunk/include/
LOCAL_SRC_FILES := chipmunk/src/chipmunk.c \
chipmunk/src/cpBody.c \
chipmunk/src/cpSpace.c \
chipmunk/src/cpSpaceStep.c \
chipmunk/src/cpBBTree.c \
chipmunk/src/cpHashSet.c \
chipmunk/src/cpShape.c \
chipmunk/src/cpBB.c \
chipmunk/src/cpCollision.c \
chipmunk/src/cpSweep1D.c \
chipmunk/src/cpPolyShape.c \
chipmunk/src/cpSpaceQuery.c \
chipmunk/src/cpVect.c \
chipmunk/src/cpArray.c \
chipmunk/src/cpArbiter.c \
chipmunk/src/cpSpaceComponent.c \
chipmunk/src/cpSpaceHash.c \
chipmunk/src/cpSpatialIndex.c \
chipmunk/src/constraints/cpPinJoint.c \
chipmunk/src/constraints/cpGearJoint.c \
chipmunk/src/constraints/cpSlideJoint.c \
chipmunk/src/constraints/cpRotaryLimitJoint.c \
chipmunk/src/constraints/cpRatchetJoint.c \
chipmunk/src/constraints/cpConstraint.c \
chipmunk/src/constraints/cpSimpleMotor.c \
chipmunk/src/constraints/cpGrooveJoint.c \
chipmunk/src/constraints/cpDampedSpring.c \
chipmunk/src/constraints/cpPivotJoint.c \
chipmunk/src/constraints/cpDampedRotarySpring.c
 
#include $(BUILD_SHARED_LIBRARY)
include $(BUILD_STATIC_LIBRARY)




LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)	
LOCAL_CPPFLAGS := -std=c++11
LOCAL_MODULE    := libgl2jni
LOCAL_CFLAGS    := -Werror
LOCAL_SHARED_LIBRARIES := chipmunk
LOCAL_PATH:= $(call my-dir)/../../samples/android-ndk/hello-gl2/jni
LOCAL_SRC_FILES := gl_code.cpp missilecommand/missilecommand.cpp 
LOCAL_LDLIBS    := -llog -lGLESv2 -lstdc++ -ljnigraphics

include $(BUILD_SHARED_LIBRARY)

