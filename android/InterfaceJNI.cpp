#include "InterfaceJNI.h"
#include "platform/android/jni/JniHelper.h"
#include <jni.h>
#include <android/log.h>

using namespace cocos2d;


void InterfaceJNI::getLocation()
{
	JavaVM* jvm = JniHelper::getJavaVM();
	int status;
	JNIEnv *env;
	jmethodID mid;

	bool isAttached = false;

	CCLog("InterfaceJNI::getLocation()");

	// Get Status
	status = jvm->GetEnv((void **) &env, JNI_VERSION_1_6);



	if(status < 0)
	{
		//LOGE("callback_handler: failed to get JNI environment, " // "assuming native thread");
		status = jvm->AttachCurrentThread(&env, NULL);
		CCLog("Status 2: %d", status);
		if(status < 0)
		{
			// LOGE("callback_handler: failed to attach " // "current thread");
			return;
		}
		isAttached = true;
		CCLog("Status isAttached: %d", isAttached);
	}
	//-----------------------------------------------------------

	CCLog("Status: %d", status);

	jclass mClass = env->FindClass("org/mumblecore/location/CClocation");

	CCLog("jClass Located?");

	mid = env->GetStaticMethodID(mClass, "getLocation", "()V");

	CCLog("mID: %d", mid);

	if (mid!=0)
		env->CallStaticVoidMethod(mClass, mid);
			//-----------------------------------------------------------
	CCLog("Finish");
	if(isAttached)
		jvm->DetachCurrentThread();

	return;
}





