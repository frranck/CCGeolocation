//
//  CoreLocation.m
//
//  Created by franck on 4/12/13.
//
//

#import "CoreLocation.h"
#include "CoreLocation_interface.h"
#include "CCCoreLocation.h"

@implementation CoreLocation

static CoreLocationImpl * myinstanceT=NULL;

CoreLocationImpl * CoreLocationImpl::getInstance() {
    if (myinstanceT==NULL) {
        myinstanceT=new CoreLocationImpl();
    }
    return myinstanceT;
}

CoreLocationImpl::CoreLocationImpl( void )
: self( NULL )
{   }

CoreLocationImpl::~CoreLocationImpl( void )
{
    NSLog(@"WARNING CoreLocationImpl::~CoreLocationImpl" );
    
    [(id)self dealloc];
}

bool CoreLocationImpl::init( void )
{
    self = [[CoreLocation alloc] init];
    [(id)self startStandardUpdates];
    return true;
}

void CoreLocationImpl::dealloc ( void ) {
    [(id)self dealloc];

}

- (void)dealloc
{
    [locationManager release];
    [super dealloc];
}

- (id)init
{
    if ((self = [super init]) != nil) {
        locationManager = nil;
    }
    return self;
}

- (void)startStandardUpdates
{
    // Create the location manager if this object does not already have one
    if (locationManager == nil)
        locationManager = [[CLLocationManager alloc] init];
    if ([CLLocationManager locationServicesEnabled]) {
        [locationManager setDelegate:self];
        [locationManager
         setDesiredAccuracy:kCLLocationAccuracyBestForNavigation];
        [locationManager setDistanceFilter:kCLDistanceFilterNone];
        if (NSFoundationVersionNumber > NSFoundationVersionNumber_iOS_7_1) {
            [locationManager requestWhenInUseAuthorization];
        }
        [locationManager startUpdatingLocation];
    }
}

- (void)locationManager:(CLLocationManager *)manager
    didUpdateToLocation:(CLLocation *)newLocation
           fromLocation:(CLLocation *)oldLocation
{
    if ([newLocation horizontalAccuracy] < 0)
        return;
    if (-[[newLocation timestamp] timeIntervalSinceNow] < 5.0)
        
        NSLog([NSString stringWithFormat:
         @"Altitude: %f Latitude: %f Longitude: %f"
         @" Course: %f Speed: %f",
         [newLocation altitude],
         [newLocation coordinate].latitude,
         [newLocation coordinate].longitude,
         [newLocation course],
               [newLocation speed]]);
    
    CCCoreLocation::getInstance()->getLocationDone((float) [newLocation coordinate].latitude, (float) [newLocation coordinate].longitude);
    
    [locationManager stopUpdatingLocation];

}

@end
