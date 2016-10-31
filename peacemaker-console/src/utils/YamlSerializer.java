package utils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Stack;

import org.yaml.snakeyaml.Yaml;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class YamlSerializer implements Serializer {

	private Stack stack = new Stack();
	private File file;

	public YamlSerializer(File file) {
		this.file = file;
	}

	public void push(Object o) {
		stack.push(o);
	}

	public Object pop() {
		return stack.pop();
	}

	@SuppressWarnings("unchecked")
	public void read() throws Exception {
		// ObjectInputStream is = null;
		YamlReader reader = null;

		try {
			reader = new YamlReader(new FileReader(file));
			stack = (Stack) reader.read();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}

	public void write() throws Exception {
		YamlWriter writer = null;

		try {
			// XStream xstream = new XStream(new DomDriver());
			// os = xstream.createObjectOutputStream(new FileWriter(file));
			writer = new YamlWriter(new FileWriter(file));
			writer.write(stack);
			// os.writeObject(stack);
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

}
