#include "hello.h"
#include <iostream>
#include <string>

JNIEXPORT jstring JNICALL Java_my_hello_Hello_say(JNIEnv *env, jobject obj,
                                                  jstring s) {
  std::cout << "... Begin in C++ ... \n";
  jclass cls = env->GetObjectClass(obj);
  jfieldID fid_number = env->GetFieldID(cls, "number", "I");
  if (fid_number == nullptr) {
    return nullptr;
  }
  jint number = env->GetIntField(obj, fid_number);
  std::cout << "Get the member of class, the number: " << number << "\n";
  number = 5678;
  env->SetIntField(obj, fid_number, number);

  const char *in_str = env->GetStringUTFChars(s, nullptr);
  if (in_str == nullptr) {
    return nullptr;
  }
  std::cout << "Received string: " << in_str << "\n";
  env->ReleaseStringUTFChars(s, in_str);
  std::cout << "... End in C++ ..." << std::endl;

  std::string out;
  out = "ok";
  return env->NewStringUTF(out.c_str());
}
