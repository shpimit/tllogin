package mio.tllogin.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.regex.Pattern;

public class XSSRequestWrapper extends HttpServletRequestWrapper {

    private static Pattern[] patterns = new Pattern[] {
        Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
        Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE| Pattern.DOTALL),
        Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE| Pattern.DOTALL),
        Pattern.compile("</script>", Pattern.CASE_INSENSITIVE),
        Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE| Pattern.MULTILINE | Pattern.DOTALL),
        Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE| Pattern.MULTILINE | Pattern.DOTALL),
        Pattern.compile("expression\\((.*?)\\)",Pattern.CASE_INSENSITIVE| Pattern.MULTILINE | Pattern.DOTALL),
        Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),
        Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),
        Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE| Pattern.MULTILINE | Pattern.DOTALL)
    };

    public XSSRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getHeader(String name) {
//        return super.getHeader(name);
        String value = super.getHeader(name);

        return stripXSS(value);
    }

    @Override
    public String getParameter(String name) {
//        return super.getParameter(name);
        String value = super.getParameter(name);

        return stripXSS(value);
    }

    @Override
    public String[] getParameterValues(String name) {
//        return super.getParameterValues(name);
        String[] values = super.getParameterValues(name);

        if (values == null) {
            return null;
        }

        int count = values.length;
        String[] encodedValues = new String[count];

        for (int i = 0; i < count; i++) {
            encodedValues[i] = stripXSS(values[i]);
        }
        return encodedValues;
    }

    private String stripXSS(String value) {

        if (value != null) {
            // ESAPI library 이용하여 XSS 필터를 적용하려면 아래 코드의 커맨트 를 제거하고 사용한다. 강력추천!!
            // value = ESAPI.encoder().canonicalize(value);

            // null 문자를 제거한다.
            value = value.replaceAll("\0", "");

            // 패턴을 포함하는 입력에 대해 <, > 을 인코딩한다.
            for (Pattern scriptPattern : patterns) {
                if ( scriptPattern.matcher(value).find() ) {
                    System.out.println("match.....");
                    value=value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
                }
            }
        }
        return value;
    }
}
