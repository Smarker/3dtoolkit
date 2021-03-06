/*
 *  Copyright 2012 The WebRTC Project Authors. All rights reserved.
 *
 *  Use of this source code is governed by a BSD-style license
 *  that can be found in the LICENSE file in the root of the source
 *  tree. An additional intellectual property rights grant can be found
 *  in the file PATENTS.  All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */

#ifndef WEBRTC_CONDUCTOR_H_
#define WEBRTC_CONDUCTOR_H_

#include <deque>
#include <map>
#include <memory>
#include <set>
#include <string>

#include "peer_connection_client.h"
#include "main_window.h"
#include "data_channel_handler.h"
#include "webrtc/api/mediastreaminterface.h"
#include "webrtc/api/peerconnectioninterface.h"

namespace webrtc
{
	class VideoCaptureModule;
}

namespace cricket
{
	class VideoRenderer;
}

class Conductor : public webrtc::PeerConnectionObserver,
	public webrtc::CreateSessionDescriptionObserver,
    public PeerConnectionClientObserver,
	public MainWindowCallback,
	public DataChannelCallback
{
public:
	enum CallbackID 
	{
		MEDIA_CHANNELS_INITIALIZED = 1,
		PEER_CONNECTION_CLOSED,
		SEND_MESSAGE_TO_PEER,
		NEW_STREAM_ADDED,
		STREAM_REMOVED,
	};

	Conductor(PeerConnectionClient* client, MainWindow* main_window);

	bool connection_active() const;

	void SetTurnCredentials(const std::string& username, const std::string& password);

	virtual void Close();

protected:
	~Conductor();

	bool InitializePeerConnection();

	bool ReinitializePeerConnectionForLoopback();

	bool CreatePeerConnection(bool dtls);

	void DeletePeerConnection();

	void EnsureStreamingUI();

	void AddStreams();

	//-------------------------------------------------------------------------
	// PeerConnectionObserver implementation.
	//-------------------------------------------------------------------------

	void OnSignalingChange(
		webrtc::PeerConnectionInterface::SignalingState new_state) override {};

	void OnAddStream(
		rtc::scoped_refptr<webrtc::MediaStreamInterface> stream) override;

	void OnRemoveStream(
		rtc::scoped_refptr<webrtc::MediaStreamInterface> stream) override;

	void OnDataChannel(
		rtc::scoped_refptr<webrtc::DataChannelInterface> channel) override;

	void OnRenegotiationNeeded() override {}

	void OnIceConnectionChange(
		webrtc::PeerConnectionInterface::IceConnectionState new_state) override {};

	void OnIceGatheringChange(
		webrtc::PeerConnectionInterface::IceGatheringState new_state) override {};

	void OnIceCandidate(const webrtc::IceCandidateInterface* candidate) override;

	void OnIceConnectionReceivingChange(bool receiving) override {}

	//-------------------------------------------------------------------------
	// PeerConnectionClientObserver implementation.
	//-------------------------------------------------------------------------

	void OnSignedIn() override;

	void OnDisconnected() override;

	void OnPeerConnected(int id, const std::string& name) override;

	void OnPeerDisconnected(int id) override;

	void OnMessageFromPeer(int peer_id, const std::string& message) override;

	void OnMessageSent(int err) override;

	void OnServerConnectionFailure() override;

	//-------------------------------------------------------------------------
	// MainWndCallback implementation.
	//-------------------------------------------------------------------------

	void StartLogin(const std::string& server, int port) override;

	void DisconnectFromServer() override;

	void ConnectToPeer(int peer_id) override;

	void DisconnectFromCurrentPeer() override;

	void UIThreadCallback(int msg_id, void* data) override;

	// DataChannelCallback implementation.
	bool SendInputData(const std::string& message) override;

	// CreateSessionDescriptionObserver implementation.
	void OnSuccess(webrtc::SessionDescriptionInterface* desc) override;

	void OnFailure(const std::string& error) override;

protected:
	// Send a message to the remote peer.
	void SendMessage(const std::string& json_object);

	int peer_id_;
	bool loopback_;
	rtc::scoped_refptr<webrtc::PeerConnectionInterface> peer_connection_;
	rtc::scoped_refptr<webrtc::PeerConnectionFactoryInterface>
		peer_connection_factory_;

	PeerConnectionClient* client_;
	rtc::scoped_refptr<webrtc::DataChannelInterface> data_channel_;
	MainWindow* main_window_;
	std::deque<std::string*> pending_messages_;
	std::map<std::string, rtc::scoped_refptr<webrtc::MediaStreamInterface>>
		active_streams_;

	std::string server_;
	std::string turn_username_;
	std::string turn_password_;
};

#endif // WEBRTC_CONDUCTOR_H_
