# This makefile will let the Android NDK build a
# libchipmunk.so to go with your 2D Android game.
 
# download the Chipmunk sources and do
# $ cd <chipmunk-physics>
# $ git checkout Chipmunk-6.0.3 # if you downloaded from source
# $ mkdir -p modules/chipmunk && cd modules/chipmunk
# $ cat >Android.mk <<EOF
 
LOCAL_HOME := $(dir $(lastword $(MAKEFILE_LIST)))
include $(CLEAR_VARS)
LOCAL_MODULE := chipmunk
# using floats for convenience: GL_DOUBLE is not available on opengl es
# allowing private access for chipmunk-draw library
LOCAL_CFLAGS := -std=c99 -DCP_USE_DOUBLES=0 -DCP_ALLOW_PRIVATE_ACCESS=1
LOCAL_EXPORT_CFLAGS := -DCP_USE_DOUBLES=0 -DCP_ALLOW_PRIVATE_ACCESS=1
LOCAL_C_INCLUDES := $(LOCAL_HOME)/../../include/chipmunk
-DCP_ALLOW_PRIVATE_ACCESS=1
LOCAL_EXPORT_C_INCLUDES := $(LOCAL_HOME)/../../include/
LOCAL_PATH := $(LOCAL_HOME)../../src
LOCAL_SRC_FILES := ./chipmunk.c \
./cpBody.c \
./cpSpace.c \
./cpSpaceStep.c \
./cpBBTree.c \
./cpHashSet.c \
./cpShape.c \
./cpBB.c \
./cpCollision.c \
./cpSweep1D.c \
./cpPolyShape.c \
./cpSpaceQuery.c \
./cpVect.c \
./cpArray.c \
./cpArbiter.c \
./cpSpaceComponent.c \
./cpSpaceHash.c \
./cpSpatialIndex.c \
./constraints/cpPinJoint.c \
./constraints/cpGearJoint.c \
./constraints/cpSlideJoint.c \
./constraints/cpRotaryLimitJoint.c \
./constraints/cpRatchetJoint.c \
./constraints/cpConstraint.c \
./constraints/cpSimpleMotor.c \
./constraints/cpGrooveJoint.c \
./constraints/cpDampedSpring.c \
./constraints/cpPivotJoint.c \
./constraints/cpDampedRotarySpring.c
 
include $(BUILD_SHARED_LIBRARY)