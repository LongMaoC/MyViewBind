package com.example;

import com.example.annotation.BindView;
import com.google.auto.service.AutoService;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

@AutoService(Processor.class)
public class MyProcessor extends AbstractProcessor {

    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<String>();
        types.add(BindView.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_7;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Map<String, ClassEntey> map = new LinkedHashMap<>();
        for (Element element : roundEnv.getElementsAnnotatedWith(BindView.class)) {
            if (element.getKind() == ElementKind.FIELD) {
                String className = element.getEnclosingElement().toString();

                ClassEntey classEntey = map.get(className);
                if (classEntey == null) {
                    String parkageNmae = className.substring(0, className.toString().lastIndexOf("."));
                    String classSimpleName = className.substring(
                            className.lastIndexOf(".") + 1, className.length()
                    ) + "_BindView";
                    classEntey = ClassEntey.create(classSimpleName, parkageNmae);
                    map.put(element.getEnclosingElement().toString(), classEntey);
                }
                AttrEntey attrEntey = AttrEntey.create(
                        element.getSimpleName().toString(),
                        element.asType().toString().substring(
                                element.asType().toString().lastIndexOf(".") + 1, element.asType().toString().length()
                        ),
                        element.getAnnotation(BindView.class).value()
                );
                classEntey.addAttr(attrEntey);
            }
        }

        for (Map.Entry<String, ClassEntey> entry : map.entrySet()) {
            try {
                List<AttrEntey> arrts = entry.getValue().getArrts();
                Set<String> setImp = new HashSet<>();
                for (AttrEntey entey : arrts) {
                    setImp.add(entey.getType());
                }
                createJava(entry.getValue(), setImp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private void createJava(ClassEntey entry, Set<String> setImp) throws Exception {
        JavaFileObject filerClassFile = filer.createSourceFile(entry.getParkageName() + "." + entry.getClassSimpleName(), new Element[]{});
        Writer writer = filerClassFile.openWriter();
        PrintWriter pw = new PrintWriter(writer);
        pw.println("package " + entry.getParkageName() + ";\n");
        pw.println("import android.view.View;");
        pw.println("import android.util.Log;");

        for (String importStr : setImp) {
            pw.println("import android.widget." + importStr + ";");
        }

        pw.println("\n");
        pw.println("public class " + entry.getClassSimpleName() + " {\n");

        pw.println("\tpublic " + entry.getClassSimpleName() + "("+entry.getClassSimpleName().replace("_BindView","")+" activity,View rootView) {\n");
        pw.println("\t\tLog.e(\"TAG\",\"name = \"+\""+entry.getClassSimpleName()+"\");");
        for (AttrEntey attr : entry.getArrts()) {
            pw.println("\t\tactivity." +attr.getName()+" = ("+attr.getType()+")rootView.findViewById("+attr.getValue()+");");
        }

        pw.println("    }\n");
        pw.println("}\n");
        pw.flush();
        writer.close();
    }

    private void text(Element e) {
        String attributeName = e.getSimpleName().toString();
        String attributeType = e.asType().toString().substring(e.asType().toString().lastIndexOf(".") + 1, e.asType().toString().length());
        String className = e.getEnclosingElement().toString();
        int annotationValue = e.getAnnotation(BindView.class).value();
        String simpleName = e.getSimpleName().toString();
        String parkageNmae = className.substring(0, className.toString().lastIndexOf("."));
        print("asType = " + e.asType().toString());
        print("attributeName = " + attributeName);
        print("attributeType = " + attributeType);
        print("className = " + className);
        print("annotationValue = " + annotationValue);
        print("simpleName = " + simpleName);
        print("parkageNmae = " + parkageNmae);
    }

    public void print(String str) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, str);
    }
}
