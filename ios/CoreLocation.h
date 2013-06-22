//
//  CoreLocation.h
//
//  Created by franck on 4/12/13.
//
//

#import <CoreLocation/CoreLocation.h>


#import <Foundation/Foundation.h>

@interface CoreLocation : NSObject <CLLocationManagerDelegate>
{
CLLocationManager *locationManager;
}



@end
