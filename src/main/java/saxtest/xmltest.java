package saxtest;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class xmltest {
	public static void main(String[] args) throws Exception {
		// SAX解析
		// 1、获取解析工厂
		SAXParserFactory factory = SAXParserFactory.newInstance();
		// 2、从解析工厂获取解析器
		SAXParser parse = factory.newSAXParser();
		// 3、编写处理器
		// 4、加载文档 Document 注册处理器
		PersonHandler handler = new PersonHandler();
		// 5、解析
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("people.xml");
		InputSource inputSource = new InputSource(inputStream);
		parse.parse(inputSource, handler);

		List<person> persons = handler.getPersons();
		for (person p : persons) {
			System.out.println(p.getName() + "----" + p.getAge());
		}
	}
}

class PersonHandler extends DefaultHandler {
	private List<person> persons;
	private person p;
	private String tag;

	public void startDocument() throws SAXException {
		System.out.println("startDocument-------");
		persons = new ArrayList<>();
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		System.out.println("startElement-------" + qName);
		if (null != qName) {
			tag = qName;
			if (tag.equals("person")) {
				p = new person();
			}
		}
	}

	public void characters(char[] ch, int start, int length) throws SAXException {
		String contents = new String(ch, start, length);
		System.out.println("characters--------" + contents);
		if (tag != null) {
			if (tag.equals("name")) {
				p.setName(contents);
			} else if (tag.equals("age")) {
				if (contents.length() > 0)
					p.setAge(Integer.valueOf(contents));
			}
		}
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		System.out.println("endElement-------" + qName);
		if (null != qName) {
			if (qName.equals("person"))
				persons.add(p);
		}
		tag = null;
	}

	public void endDocument() throws SAXException {
		System.out.println("endDocument-------");
	}

	public List<person> getPersons() {
		return persons;
	}

	public void setPersons(List<person> persons) {
		this.persons = persons;
	}

}
