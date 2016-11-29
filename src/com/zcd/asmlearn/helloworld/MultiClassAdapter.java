package com.zcd.asmlearn.helloworld;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;

/**
 * 转换链
 * 
 * @author zhangyida
 * 
 */
public class MultiClassAdapter extends ClassAdapter {

	protected ClassVisitor[] cvs;

//	public MultiClassAdapter(ClassVisitor[] cvs) {
//		this.cvs = cvs;
//	}

	public MultiClassAdapter(ClassVisitor cv) {
		super(cv);
	}

	@Override
	public void visit(int paramInt1, int paramInt2, String paramString1,
			String paramString2, String paramString3,
			String[] paramArrayOfString) {
//		for (ClassVisitor cv : cvs) {
			cv.visit(paramInt1, paramInt2, paramString1, paramString2,
					paramString3, paramArrayOfString);
//		}
	}

}
