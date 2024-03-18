package org.mediasoup.droid.demo.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.inputmethod.EditorInfo;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;

import com.nolovr.core.mediartc.lib.PeerConnectionUtils;
import com.nolovr.core.mediartc.lib.RoomClient;
import com.nolovr.core.mediartc.lib.RoomOptions;

import org.mediasoup.droid.demo.R;
import org.mediasoup.droid.demo.databinding.ViewMeBindingImpl;
import org.mediasoup.droid.demo.vm.MeProps;

public class MeView extends RelativeLayout {

    private static final String TAG = "MeView";

    public MeView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public MeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MeView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MeView(
            @NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    ViewMeBindingImpl mBinding;

    private void init(Context context) {
        Log.d(TAG, "init: ");
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.view_me, this, true);
        // TODO: 2024/3/18 干掉
        //mBinding.peerView.videoRenderer.init(PeerConnectionUtils.getEglContext(), null);
    }

    public void setProps(MeProps props, final RoomClient roomClient) {
        Log.d(TAG, "setProps: props=" + props.getMe());
        RoomOptions roomOptions = roomClient.getRoomOptions();
        Log.d(TAG, "setProps: roomClient=" + roomOptions);
        boolean preview = roomOptions.isPreview();
        boolean activated = mBinding.peerView.videoRenderer.isActivated();
        boolean enabled = mBinding.peerView.videoRenderer.isEnabled();
        Log.d(TAG, "setProps: preview=" + preview);
        Log.d(TAG, "setProps: activated=" + activated);
        Log.d(TAG, "setProps: enabled=" + enabled);
        if (preview) {
            mBinding.peerView.videoRenderer.init(PeerConnectionUtils.getEglContext(), null);
            Log.e(TAG, "setProps: exec init ......");
        }else {
            mBinding.peerView.videoRenderer.release();
            Log.e(TAG, "setProps: exec release ........");
        }
        // set view model.
        mBinding.peerView.setPeerViewProps(props);

        // register click listener.
        mBinding.peerView.info.setOnClickListener(
                view -> {
                    Boolean showInfo = props.getShowInfo().get();
                    props.getShowInfo().set(showInfo != null && showInfo ? Boolean.FALSE : Boolean.TRUE);
                });

        mBinding.peerView.meDisplayName.setOnEditorActionListener(
                (textView, actionId, keyEvent) -> {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        roomClient.changeDisplayName(textView.getText().toString().trim());
                        return true;
                    }
                    return false;
                });
        mBinding.peerView.stats.setOnClickListener(
                view -> {
                    // TODO(HaiyangWU): Handle inner click event;
                });

        mBinding.peerView.videoRenderer.setZOrderMediaOverlay(true);

        // set view model.
        mBinding.setMeProps(props);
        Log.d(TAG, "setProps: " + props.toString());

        // register click listener.
        mBinding.mic.setOnClickListener(
                view -> {
                    if (MeProps.DeviceState.ON.equals(props.getMicState().get())) {
                        roomClient.muteMic();
                    } else {
                        roomClient.unmuteMic();
                    }
                });
        mBinding.cam.setOnClickListener(
                view -> {
                    if (MeProps.DeviceState.ON.equals(props.getCamState().get())) {
                        roomClient.disableCam();
                    } else {
                        roomClient.enableCam();
                    }
                });
        mBinding.changeCam.setOnClickListener(view -> roomClient.changeCam());
        mBinding.share.setOnClickListener(
                view -> {
                    if (MeProps.DeviceState.ON.equals(props.getShareState().get())) {
                        roomClient.disableShare();
                    } else {
                        roomClient.enableShare();
                    }
                });
    }
}
