package com.zcd.asmlearn.helloworld;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;

/**
 * 通过下面继承的的一些方法，但是没有具体的使用super，所以可以达到移除外部类和内部类的目的
 * 
 * @author zhangyida
 * 
 */
public class RemoveDebugAdapter extends ClassAdapter {

	public RemoveDebugAdapter(ClassVisitor cv) {
		super(cv);
	}

	@Override
	public void visitSource(String paramString1, String paramString2) {
	}

	@Override
	public void visitOuterClass(String paramString1, String paramString2,
			String paramString3) {
	}

	@Override
	public void visitInnerClass(String paramString1, String paramString2,
			String paramString3, int paramInt) {
	}

}
