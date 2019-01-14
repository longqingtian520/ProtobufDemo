package com.criss.wang.service;

import org.springframework.stereotype.Service;

import com.criss.wang.entity.proto.ColourProto.BlueMessage;
import com.criss.wang.entity.proto.ColourProto.ColourMessage;
import com.criss.wang.entity.proto.ColourProto.RedMessage;
import com.criss.wang.entity.proto.ParentMessageProto.ChildMessage;
import com.criss.wang.entity.proto.ParentMessageProto.ParentMessage;
import com.google.protobuf.Any;

@Service
public class PackService {

	/**
	 * 嵌套一个类
	 *
	 * @return
	 */
	public static ParentMessage createMessage() {
		// Create child message
		ChildMessage.Builder childMessageBuilder = ChildMessage.newBuilder();
		childMessageBuilder.setText("Child Text");
		// Create parent message
		ParentMessage.Builder parentMessageBuilder = ParentMessage.newBuilder();
		parentMessageBuilder.setText("Parent Text");
		parentMessageBuilder.setChildMessage(Any.pack(childMessageBuilder.build()));
		// Return message
		return parentMessageBuilder.build();
	}

	/**
	 * 嵌套两个类
	 *
	 * @return
	 */
	public static ColourMessage createColourMessage() {
		// create child message
		RedMessage.Builder redMessage = RedMessage.newBuilder();
		redMessage.setName("red colour");
		redMessage.setNumber(2);
		BlueMessage.Builder blueMessage = BlueMessage.newBuilder();
		blueMessage.setName("blue colour");
		blueMessage.setNumber(3);
		// create parent message
		ColourMessage.Builder colourMessage = ColourMessage.newBuilder();
		colourMessage.setChildMessage(Any.pack(redMessage.build()));
		colourMessage.setChildMessage(Any.pack(blueMessage.build()));

		colourMessage.setRedMessage(Any.pack(redMessage.build()));
		colourMessage.setBlueMessage(Any.pack(blueMessage.build()));
		// return message
		return colourMessage.build();
	}

	public static void main(String[] args) {
		System.out.println(createMessage().getText());
		System.out.println(createMessage().getChildMessage().getTypeUrl());
	}

}
