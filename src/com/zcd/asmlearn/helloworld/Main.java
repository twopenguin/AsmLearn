package com.zcd.asmlearn.helloworld;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.InvocationTargetException;
import java.security.ProtectionDomain;

import org.junit.Test;
import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import com.zcd.asmlearn.helloworld.classloader.MyClassLoader;
import com.zcd.asmlearn.helloworld.method.ModifyMethodClassAdapter;
import com.zcd.asmlearn.helloworld.method.Person;

public class Main {

	@Test
	public void testPersonSleep(){
		Person person = new Person();
		person.sleep();
	}
	
	@Test
	public void testMethodModify() throws InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			NoSuchFieldException, SecurityException, InvocationTargetException,
			NoSuchMethodException, IOException {
		
		ClassReader classReader = new ClassReader(
				"com.zcd.asmlearn.helloworld.method.Person");
		ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		ClassAdapter classAdapter = new ModifyMethodClassAdapter(classWriter);
		classReader.accept(classAdapter, ClassReader.SKIP_DEBUG);

		byte[] classFile = classWriter.toByteArray();

		MyClassLoader classLoader = new MyClassLoader();
		@SuppressWarnings("rawtypes")
		Class clazz = classLoader.defineClass(
				"com.zcd.asmlearn.helloworld.method.Person", classFile);
		Object obj = clazz.newInstance();
		System.out.println(clazz.getDeclaredField("name").get(obj));
		clazz.getDeclaredMethod("sleep").invoke(obj);
	}

	/**
	 * 一个ClassLoader要想加载所有的类，你需要把代码放入同一个ClassFileTransformer，该类定义在java.lang.
	 * instrument中
	 * 
	 * @param agentArgs
	 * @param instrumentation
	 */
	public static void premain(String agentArgs, Instrumentation instrumentation) {
		instrumentation.addTransformer(new ClassFileTransformer() {
			@Override
			public byte[] transform(ClassLoader loader, String className,
					Class<?> classBeingRedefined,
					ProtectionDomain protectionDomain, byte[] classfileBuffer)
					throws IllegalClassFormatException {
				ClassReader reader = new ClassReader(classfileBuffer);
				ClassWriter writer = new ClassWriter(0);
				ClassAdapter adapter = new ChangeVersionAdapter(writer);
				reader.accept(adapter, 0);
				return writer.toByteArray();
			}
		});
	}

	/**
	 * 测试 ClassAdapter
	 * 
	 */
	public void testClassAdapter() {
		byte[] b = new byte[1024];
		ClassReader reader = new ClassReader(b);
		ClassWriter writer = new ClassWriter(0);
		ClassAdapter adapter = new ClassAdapter(writer);
		reader.accept(adapter, 0);
		writer.toByteArray();
	}

	/**
	 * 测试 简单 ClassReader + ClassWriter
	 */
	public void testMerge() {
		byte[] b = new byte[1024];
		ClassReader reader = new ClassReader(b);
		ClassWriter writer = new ClassWriter(0);
		reader.accept(writer, 0);
		writer.toByteArray();
	}

	/**
	 * 测试ClassWriter
	 */
	@Test
	public void testClassWriter() {
		ClassWriter writer = new ClassWriter(0);
		writer.visit(Opcodes.V1_5, Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT
				+ Opcodes.ACC_INTERFACE,
				"com/zcd/asmlearn/helloworld/Comparable", null,
				"java/lang/Object",
				new String[] { "com/zcd/asmlearn/helloworld/Mesurable" });

		writer.visitField(
				Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC + Opcodes.ACC_FINAL,
				"LESS", "I", null, new Integer(-1)).visitEnd();
		writer.visitField(
				Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC + Opcodes.ACC_FINAL,
				"EQUAL", "I", null, new Integer(-1)).visitEnd();
		writer.visitField(
				Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC + Opcodes.ACC_FINAL,
				"GREATER", "I", null, new Integer(-1)).visitEnd();
		// 修饰符、属性或者方法名字、描述符、参数对应泛型、异常列表
		writer.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT,
				"compareTo", "(Ljava/lang/Object;)I", null, null).visitEnd();
		writer.visitEnd();
		byte b[] = writer.toByteArray();
		MyClassLoader loader = new MyClassLoader();
		Class clazz = loader.defineClass(
				"com.zcd.asmlearn.helloworld.Comparable", b);
		System.out.println(clazz);
	}

	/**
	 * 测试ClassReader
	 * 
	 * @throws IOException
	 */
	@Test
	public void testClassReader() throws IOException {
		ClassPrinter printer = new ClassPrinter();
		ClassReader reader = new ClassReader("java.lang.Runnable");
		reader.accept(printer, 0);
	}

}
