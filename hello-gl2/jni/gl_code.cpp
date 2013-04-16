/*
 * Copyright (C) 2009 The Android Open Source Project
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

// OpenGL ES 2.0 code


#include <jni.h>
#include <android/log.h>
#include <android/bitmap.h>

#include <GLES2/gl2.h>
#include <GLES2/gl2ext.h>


#include <stdio.h>
#include <stdlib.h>
#include <math.h>

#include "missilecommand/missilecommand.h"


#define  LOG_TAG    "libgl2jni"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

Missilecommand mc;
uint32_t  *test_pixels;
bool hasPixels = false;
int WIDTH;
int HEIGHT;

static void printGLString(const char *name, GLenum s) {
    const char *v = (const char *) glGetString(s);
    LOGI("GL %s = %s\n", name, v);
}

static void checkGlError(const char* op) {
    for (GLint error = glGetError(); error; error
            = glGetError()) {
        LOGI("after %s() glError (0x%x)\n", op, error);
    }
}

static const char gVertexShader[] = 
    "attribute vec4 vPosition;\n"
    "void main() {\n"
    "  gl_Position = vPosition;\n"
    "}\n";

static const char gFragmentShader[] = 
    "precision mediump float;\n"
    "void main() {\n"
    "  gl_FragColor = vec4(0.0, 1.0, 0.0, 1.0);\n"
    "}\n";

GLuint loadShader(GLenum shaderType, const char* pSource) {
    GLuint shader = glCreateShader(shaderType);
    if (shader) {
        glShaderSource(shader, 1, &pSource, NULL);
        glCompileShader(shader);
        GLint compiled = 0;
        glGetShaderiv(shader, GL_COMPILE_STATUS, &compiled);
        if (!compiled) {
            GLint infoLen = 0;
            glGetShaderiv(shader, GL_INFO_LOG_LENGTH, &infoLen);
            if (infoLen) {
                char* buf = (char*) malloc(infoLen);
                if (buf) {
                    glGetShaderInfoLog(shader, infoLen, NULL, buf);
                    LOGE("Could not compile shader %d:\n%s\n",
                            shaderType, buf);
                    free(buf);
                }
                glDeleteShader(shader);
                shader = 0;
            }
        }
    }
    return shader;
}

GLuint createProgram(const char* pVertexSource, const char* pFragmentSource) {
    GLuint vertexShader = loadShader(GL_VERTEX_SHADER, pVertexSource);
    if (!vertexShader) {
        return 0;
    }

    GLuint pixelShader = loadShader(GL_FRAGMENT_SHADER, pFragmentSource);
    if (!pixelShader) {
        return 0;
    }

    GLuint program = glCreateProgram();
    if (program) {
        glAttachShader(program, vertexShader);
        checkGlError("glAttachShader");
        glAttachShader(program, pixelShader);
        checkGlError("glAttachShader");
        glLinkProgram(program);
        GLint linkStatus = GL_FALSE;
        glGetProgramiv(program, GL_LINK_STATUS, &linkStatus);
        if (linkStatus != GL_TRUE) {
            GLint bufLength = 0;
            glGetProgramiv(program, GL_INFO_LOG_LENGTH, &bufLength);
            if (bufLength) {
                char* buf = (char*) malloc(bufLength);
                if (buf) {
                    glGetProgramInfoLog(program, bufLength, NULL, buf);
                    LOGE("Could not link program:\n%s\n", buf);
                    free(buf);
                }
            }
            glDeleteProgram(program);
            program = 0;
        }
    }
    return program;
}

GLuint gProgram;
GLuint gvPositionHandle;

bool setupGraphics(int w, int h) {
    WIDTH = w;
    HEIGHT = h;
    printGLString("Version", GL_VERSION);
    printGLString("Vendor", GL_VENDOR);
    printGLString("Renderer", GL_RENDERER);
    printGLString("Extensions", GL_EXTENSIONS);

    LOGI("setupGraphics(%d, %d)", w, h);
    gProgram = createProgram(gVertexShader, gFragmentShader);
    if (!gProgram) {
        LOGE("Could not create program.");
        return false;
    }
    gvPositionHandle = glGetAttribLocation(gProgram, "vPosition");
    checkGlError("glGetAttribLocation");
    LOGI("glGetAttribLocation(\"vPosition\") = %d\n",
            gvPositionHandle);

    glViewport(0, 0, w, h);
    checkGlError("glViewport");
    return true;
}



GLfloat gTriangleVertices1[] = { 0.0f, 0.0f,0.0f, 0.0f, 0.0f,0.0f,
                                0.0f, 0.0f,0.0f, 0.0f, 0.0f,0.0f,
                                0.0f, 0.0f,0.0f, 0.0f, 0.0f,0.0f };

void renderFrame() {
    static float grey;
    grey += 0.01f;
    if (grey > 1.0f) {
        grey = 0.0f;
    }


    int  pointsPerCity = mc.getPointsPerCity();



    int numPlanets = mc.getNumBodies();
    int numSpacemen = 1;

    int numObjects = numPlanets;

    if (mc.hasSpaceman())
    {
        numObjects += numSpacemen;
    }

    GLfloat gTriangleVertices[pointsPerCity*(numObjects)];

    int index = 0;

    for (int j = 0; j<  numPlanets;j++){
        gTriangleVertices[index] = mc.getBody(j)->p.x*2.0/WIDTH - 1.0;
        index++;
        gTriangleVertices[index] = -mc.getBody(j)->p.y*2.0/HEIGHT +1.0;
        index++;

        gTriangleVertices[index] = mc.getBody(j)->p.x*2.0/WIDTH - 1.0 + .2;
        index++;
        gTriangleVertices[index] = -mc.getBody(j)->p.y*2.0/HEIGHT +1.0;
        index++;

        gTriangleVertices[index] = mc.getBody(j)->p.x*2.0/WIDTH - 1.0;
        index++;
        gTriangleVertices[index] = -mc.getBody(j)->p.y*2.0/HEIGHT +1.0 + .2;
        index++;
     }

    if (mc.hasSpaceman())
    {
        gTriangleVertices[index] = mc.getSpaceman()->p.x*2.0/WIDTH - 1.0;
        index++;
        gTriangleVertices[index] = -mc.getSpaceman()->p.y*2.0/HEIGHT +1.0;
        index++;

        gTriangleVertices[index] = mc.getSpaceman()->p.x*2.0/WIDTH - 1.0 + .2;
        index++;
        gTriangleVertices[index] = -mc.getSpaceman()->p.y*2.0/HEIGHT +1.0;
        index++;

        gTriangleVertices[index] = mc.getSpaceman()->p.x*2.0/WIDTH - 1.0;
        index++;
        gTriangleVertices[index] = -mc.getSpaceman()->p.y*2.0/HEIGHT +1.0 + .2;
        index++;
    }


    

    glClearColor(grey, grey, grey, 1.0f);
    checkGlError("glClearColor");
    glClear( GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
    checkGlError("glClear");

    glUseProgram(gProgram);
    checkGlError("glUseProgram");

    glVertexAttribPointer(gvPositionHandle, 2, GL_FLOAT, GL_FALSE, 0, gTriangleVertices);
    checkGlError("glVertexAttribPointer");
    glEnableVertexAttribArray(gvPositionHandle);
    checkGlError("glEnableVertexAttribArray");


    //each triangle is 3 indexes
    glDrawArrays(
        GL_TRIANGLES,// enum of what to draw
        0, //starting index
        3*(numObjects));//number of indexes to draw.

    checkGlError("glDrawArrays");

    if(hasPixels)
    {
        int SIZE = 10;

        glTexImage2D(
        GL_TEXTURE_2D, 0, GL_RGBA, SIZE, SIZE, 0,
        GL_RGBA, GL_UNSIGNED_BYTE, test_pixels);

    }



}
#ifdef __cplusplus
extern "C" {
#endif

    JNIEXPORT void JNICALL Java_com_android_gl2jni_GL2JNILib_init(JNIEnv * env, jobject obj,  jint width, jint height);
    JNIEXPORT void JNICALL Java_com_android_gl2jni_GL2JNILib_step(JNIEnv * env, jobject obj);
    JNIEXPORT void JNICALL Java_com_android_gl2jni_GL2JNILib_handleTouch(JNIEnv * env, jobject obj,  jfloat width, jfloat height);
    JNIEXPORT void JNICALL Java_com_android_gl2jni_GL2JNILib_fire(JNIEnv * env, jobject obj,jfloat velocity);
    JNIEXPORT void JNICALL Java_com_android_gl2jni_GL2JNILib_getBitmap(JNIEnv * env, jobject obj,jobject bitmap);
#ifdef __cplusplus
};
#endif

JNIEXPORT void JNICALL Java_com_android_gl2jni_GL2JNILib_init(JNIEnv * env, jobject obj,  jint width, jint height)
{
    
    //setupGraphics(width, height);
    mc.createGame();
}

JNIEXPORT void JNICALL Java_com_android_gl2jni_GL2JNILib_handleTouch(JNIEnv * env, jobject obj,  jfloat x, jfloat y)
{
    mc.handleTouch(x,y);
    
}

JNIEXPORT void JNICALL Java_com_android_gl2jni_GL2JNILib_fire(JNIEnv * env, jobject obj,jfloat velocity)
{
    mc.fire(velocity);
    
}

JNIEXPORT void JNICALL Java_com_android_gl2jni_GL2JNILib_getBitmap(JNIEnv * env, jobject obj,jobject bitmap)
{
    // AndroidBitmapInfo  info;
    // uint32_t          *pixels;
    // int                ret;

    // AndroidBitmap_getInfo(env, bitmap, &info);

    // if(info.format != ANDROID_BITMAP_FORMAT_RGBA_8888) 
    // {
    //     LOGE("Bitmap format is not RGBA_8888!");
    //     //return false;
    // }else{

    // AndroidBitmap_lockPixels(env, bitmap, reinterpret_cast<void **>(&pixels));

    // // Now you can use the pixel array 'pixels', which is in RGBA format
    // test_pixels = pixels;
    // hasPixels = true;
    // }
}

JNIEXPORT void JNICALL Java_com_android_gl2jni_GL2JNILib_step(JNIEnv * env, jobject obj)
{
    mc.updateGame();
    //renderFrame();
}
