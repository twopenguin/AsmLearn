package com.zcd.asmlearn.helloworld;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

public class ChangeVersionAdapter extends ClassAdapter {

	public ChangeVersionAdapter(ClassVisitor cv) {
		super(cv);
	}

	@Override
	public void visit(int paramInt1, int paramInt2, String paramString1,
			String paramString2, String paramString3,
			String[] paramArrayOfString) {
		cv.visit(Opcodes.V1_5, paramInt2, paramString1, paramString2,
				paramString3, paramArrayOfString);
	}

}
