//
//  CCCoreLocation.cpp
//
//  Created by franck on 3/19/13.
//
//

#include "CCCoreLocation.h"
#include "cocos-ext.h"

#ifndef CC_TARGET_OS_IPHONE
#include "android/InterfaceJNI.h"
#endif

using namespace cocos2d::extension;
using namespace cocos2d;

static CCCoreLocation * myinstanceT=NULL;

CCCoreLocation * CCCoreLocation::getInstance() {
    if (myinstanceT==NULL) {
        myinstanceT=new CCCoreLocation();
    }
    return myinstanceT;
}

CCCoreLocation::CCCoreLocation() {

    _LOCATION_KEY[0]='\0';
    CCUserDefault * d=CCUserDefault::sharedUserDefault();
    std::string buff=d->getStringForKey(LOCATION_KEY, std::string(_LOCATION_KEY));
    sprintf(_LOCATION_KEY,"%s",buff.c_str());
    CCLOG("loaded _LOCATION_KEY=<%s>",_LOCATION_KEY);
    
#ifdef CC_TARGET_OS_IPHONE
    _localisationImpl=new CoreLocationImpl();
#endif
    
}

bool CCCoreLocation::isLocationAvailable() {
    if (_LOCATION_KEY[0]!='\0') {
        return true;
    }
    CCLOG("isLocationAvailable returns FALSE !!");
    return false;
}

void CCCoreLocation::getLocation(CCObject* pTarget, cocos2d::SEL_CallFuncND pSelector) {


	CCLOG("CCCoreLocation::getLocation");
    _pSelector=pSelector;
    _pTarget=pTarget;
    
#ifdef CC_TARGET_OS_IPHONE
    _localisationImpl->init();
#else
    InterfaceJNI::getLocation();
#endif
   
}

void CCCoreLocation::getLocationDone(float lati, float longi) {
    _loc.lati=lati;
    _loc.longi=longi;

    sprintf(_LOCATION_KEY,"lati=%f&longi=%f",lati, longi);
    CCUserDefault * d=CCUserDefault::sharedUserDefault();
    d->setStringForKey(LOCATION_KEY, std::string(_LOCATION_KEY));
    CCLOG("setting LOCATION_KEY=%s",_LOCATION_KEY);
    
    if (_pTarget && _pSelector)
    {
        (_pTarget->*_pSelector)((CCNode *)this, (void *) &_loc);
    }    
}

#ifndef CC_TARGET_OS_IPHONE

#ifdef __cplusplus
 extern "C" {
#endif

void Java_org_mumblecore_location_CClocation_nativeupdatePosition(JNIEnv *env, jobject thiz, jdouble a, jdouble b)
{
	CCLOG("Java_org_mumblecore_location_CClocation_nativeupdatePosition LOC %f, %f",float (a), float (b));
	CCCoreLocation::getInstance()->getLocationDone(float (a), float (b));
}

#ifdef __cplusplus
}
#endif

#endif
