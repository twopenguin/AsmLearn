package com.zcd.asmlearn.helloworld.anaysisutil;

import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ModifyMethodAdapter extends MethodAdapter {

	public ModifyMethodAdapter(MethodVisitor mv) {
		super(mv);
	}

	@Override
	public void visitCode() {
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream",
				"println", "(J)V");
	}

}
