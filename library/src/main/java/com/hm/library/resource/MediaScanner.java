package com.hm.library.resource;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;

/**
 * MediaScannerConnection
 * <p/>
 * himi on 2016-08-24 12:31
 * version V1.0
 */
public class MediaScanner extends MediaScannerConnection {

    private OnScanCompletedListener callback;

    /**
     * Constructs a new MediaScannerConnection object.
     *
     * @param context the Context object, required for establishing a connection to
     *                the media scanner service.
     * @param client  an optional object implementing the MediaScannerConnectionClient
     */
    public MediaScanner(Context context, MediaScannerConnectionClient client) {
        super(context, client);
    }

    @Override
    public void connect() {
        super.connect();
    }

    @Override
    public void disconnect() {
        super.disconnect();
        if (callback != null) {
            callback.onScanCompleted(null, null);
        }
    }

    public static void scanFile(Context context, String[] paths, String[] mimeTypes,
                                OnScanCompletedListener callback) {
        ClientProxy client = new ClientProxy(paths, mimeTypes, callback);
        MediaScanner connection = new MediaScanner(context, client);
        connection.callback = callback;
        client.mConnection = connection;
        connection.connect();
    }


    static class ClientProxy implements MediaScannerConnectionClient {
        final String[] mPaths;
        final String[] mMimeTypes;
        final OnScanCompletedListener mClient;
        MediaScanner mConnection;
        int mNextPath;

        ClientProxy(String[] paths, String[] mimeTypes, OnScanCompletedListener client) {
            mPaths = paths;
            mMimeTypes = mimeTypes;
            mClient = client;
        }

        public void onMediaScannerConnected() {
            scanNextPath();
        }

        public void onScanCompleted(String path, Uri uri) {
            if (mClient != null) {
                mClient.onScanCompleted(path, uri);
            }
            scanNextPath();
        }

        void scanNextPath() {
            if (mNextPath >= mPaths.length) {
                mConnection.disconnect();
                return;
            }
            String mimeType = mMimeTypes != null ? mMimeTypes[mNextPath] : null;
            mConnection.scanFile(mPaths[mNextPath], mimeType);
            mNextPath++;
        }
    }
}