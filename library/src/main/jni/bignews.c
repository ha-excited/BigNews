#include <jni.h>
#include <unistd.h>
#include <sys/wait.h>
#include <stdlib.h>

extern int bspatch_main(int argc, char *argv[]);

extern int bsdiff_main(int argc, char *argv[]);

int main_exec(int (*mainFunc)(int, char *), int argc, char **argv) {
    int ret = fork();
    if (0 == ret) {
        exit(mainFunc(argc, argv));
    } else if (-1 != ret) {
        wait(&ret);
    }
    return ret;
}

JNIEXPORT jboolean JNICALL
Java_ha_excited_BigNews_make(JNIEnv *env, jclass type, jstring oldFilePath_, jstring newFilePath_,
                             jstring patchPath_) {

    char *ch[5] = {0};
    ch[0] = "bspatch";
    ch[1] = (*env)->GetStringUTFChars(env, oldFilePath_, 0);
    ch[2] = (*env)->GetStringUTFChars(env, newFilePath_, 0);
    ch[3] = (*env)->GetStringUTFChars(env, patchPath_, 0);

    int ret = main_exec(bspatch_main, 4, ch);

    (*env)->ReleaseStringUTFChars(env, oldFilePath_, ch[1]);
    (*env)->ReleaseStringUTFChars(env, newFilePath_, ch[2]);
    (*env)->ReleaseStringUTFChars(env, patchPath_, ch[3]);

    return !ret;
}

JNIEXPORT jboolean JNICALL
Java_ha_excited_BigNews_diff(JNIEnv *env, jclass type, jstring oldFilePath_, jstring newFilePath_,
                             jstring patchPath_) {
    char *ch[5] = {0};
    ch[0] = "bsdiff";
    ch[1] = (*env)->GetStringUTFChars(env, oldFilePath_, 0);
    ch[2] = (*env)->GetStringUTFChars(env, newFilePath_, 0);
    ch[3] = (*env)->GetStringUTFChars(env, patchPath_, 0);

    int ret = main_exec(bsdiff_main, 4, ch);

    (*env)->ReleaseStringUTFChars(env, oldFilePath_, ch[1]);
    (*env)->ReleaseStringUTFChars(env, newFilePath_, ch[2]);
    (*env)->ReleaseStringUTFChars(env, patchPath_, ch[3]);

    return !ret;
}