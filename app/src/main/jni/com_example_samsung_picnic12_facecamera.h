/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_example_samsung_picnic12_facecamera */

#ifndef _Included_com_example_samsung_picnic12_facecamera
#define _Included_com_example_samsung_picnic12_facecamera
#ifdef __cplusplus
extern "C" {
#endif
#undef com_example_samsung_picnic12_facecamera_PERMISSIONS_REQUEST_CODE
#define com_example_samsung_picnic12_facecamera_PERMISSIONS_REQUEST_CODE 1000L
/*
 * Class:     com_example_samsung_picnic12_facecamera
 * Method:    loadCascade
 * Signature: (Ljava/lang/String;)J
 */
JNIEXPORT jlong JNICALL Java_com_example_samsung_picnic12_facecamera_loadCascade
  (JNIEnv *, jobject, jstring);

/*
 * Class:     com_example_samsung_picnic12_facecamera
 * Method:    detect
 * Signature: (JJJJ)V
 */
JNIEXPORT jint JNICALL Java_com_example_samsung_picnic12_facecamera_detect
  (JNIEnv *, jobject, jlong, jlong, jlong, jlong);

#ifdef __cplusplus
}
#endif
#endif