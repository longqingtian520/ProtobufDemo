package com.criss.wang.service;

import org.springframework.stereotype.Service;

import com.criss.wang.entity.proto.ColourProto.BlueMessage;
import com.criss.wang.entity.proto.ColourProto.ColourMessage;
import com.criss.wang.entity.proto.ParentMessageProto.ChildMessage;
import com.criss.wang.entity.proto.ParentMessageProto.ParentMessage;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;

@Service
public class UnpackService {

	public static ChildMessage readChildMessage(ParentMessage parentMessage) {
	    try {
	        return parentMessage.getChildMessage().unpack(ChildMessage.class);
	    } catch (InvalidProtocolBufferException e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	@SuppressWarnings("unchecked")
	public static Message readChildMessage(ColourMessage colourMessage) {
	    try {
	        Any childMessage = colourMessage.getChildMessage();
	        String clazzName = childMessage.getTypeUrl().split("/")[1];
	        String clazzPackage = String.format("package.%s", clazzName);
	        Class<Message> clazz = (Class<Message>) Class.forName(clazzPackage);
	        return childMessage.unpack(clazz);
	    } catch (ClassNotFoundException | InvalidProtocolBufferException e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	public static BlueMessage readChildMessageWithTypeUrl(ColourMessage colourMessage) {
	    try {
	        Any childMessage = colourMessage.getChildMessage();
	        System.out.println(childMessage.getTypeUrl());
	        String clazzName = childMessage.getTypeUrl().split("/")[1];
	        if (clazzName.equals("com.criss.wang.entity.proto.BlueMessage")) {

	            return childMessage.unpack(BlueMessage.class);
	        }
	        return null;
	    } catch (InvalidProtocolBufferException e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	public static void main(String[] args) {
		ParentMessage parentMessage = PackService.createMessage();
		ChildMessage childMessage = readChildMessage(parentMessage);
		System.out.println(childMessage.getText());
		System.out.println("================================================");
		ColourMessage colourMessage = PackService.createColourMessage();
		System.out.println("=================================================");

		BlueMessage blueMessage = readChildMessageWithTypeUrl(colourMessage);
		System.out.println(blueMessage.getName());

	}
}
