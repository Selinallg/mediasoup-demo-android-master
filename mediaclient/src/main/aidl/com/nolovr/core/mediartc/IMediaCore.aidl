package com.nolovr.core.mediartc;

import com.nolovr.core.mediartc.IRequestListener;
import com.nolovr.core.mediartc.IGlobalListener;

interface IMediaCore {

    void createRoom(String who);
    void destroyRoom(String who);

    void configChanged(String json);

    void action(String json);
    void request(String parmaJson,IRequestListener listener);

    void registerGlobalListener(String pkg, IGlobalListener listener);
    void unregisterGlobalListener(String pkg);
}