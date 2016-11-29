package com.zcd.asmlearn.helloworld;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;

/**
 * 添加字段或者方法，在visitEnd方法中操作，类似如下
 * 
 * @author zhangyida
 * 
 */
public class AddFieldAdapter extends ClassAdapter {

	private int fAcc;
	private String fName;
	private String fDesc;
	private boolean isFieldPresent;

	public AddFieldAdapter(ClassVisitor cv, int fAcc, String fName, String fDesc) {
		super(cv);
		this.fAcc = fAcc;
		this.fName = fName;
		this.fDesc = fDesc;
	}

	@Override
	public FieldVisitor visitField(int paramInt, String paramString1,
			String paramString2, String paramString3, Object paramObject) {
		if (paramString1.equals(fName)) {
			isFieldPresent = true;
		}
		return super.visitField(paramInt, paramString1, paramString2,
				paramString3, paramObject);
	}

	@Override
	public void visitEnd() {
		if (!isFieldPresent) {
			FieldVisitor visitor = cv
					.visitField(fAcc, fName, fDesc, null, null);
			if (visitor != null) {
				visitor.visitEnd();
			}
		}
		super.visitEnd();
	}

}
