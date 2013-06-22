//
//  CoreLocation_interface.h
//
//  Created by franck on 4/12/13.
//
//

#ifndef CoreLocation_interface_h
#define CoreLocation_interface_h


class CoreLocationImpl
{
public:
    CoreLocationImpl ( void );
    ~CoreLocationImpl( void );
    static CoreLocationImpl * getInstance();
    bool init( void );
    void dealloc ( void );
    
private:
    void * self;
};


#endif


