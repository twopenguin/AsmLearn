package com.zcd.asmlearn.helloworld;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

/**
 * 移除某个指定名字和描述的方法，之所以单独定义一个是因为，移除内部和外部类的方式对于移除方法和字段没有用，因为他们必须返回一个对象
 * 
 * @author zhangyida
 * 
 */
public class RemoveMethodAdapter extends ClassAdapter {

	String mName;
	String mDesc;

	public RemoveMethodAdapter(ClassVisitor cv, String mName, String mDesc) {
		super(cv);
		this.mName = mName;
		this.mDesc = mDesc;
	}

	@Override
	public MethodVisitor visitMethod(int paramInt, String paramString1,
			String paramString2, String paramString3,
			String[] paramArrayOfString) {
		if (paramString1.equals(mName) && paramString2.equals(mDesc)) {
			return null;
		}
		return cv.visitMethod(paramInt, paramString1, paramString2,
				paramString3, paramArrayOfString);
	}

}
