import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 12/10/15.
 */
public class ShellTest {

    public static void main(String[] args) {
//        execShell("mkdir /root/123");
        try {
            System.out.println(runShell("ll /root"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 运行shell脚本
     * @param shell 需要运行的shell脚本
     */
    public static void execShell(String shell){
        try {
            Runtime rt = Runtime.getRuntime();
            rt.exec(shell);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 运行shell
     *
     * @param shStr
     *            需要执行的shell
     * @return
     * @throws IOException
     */
    public static List runShell(String shStr) throws Exception {
        List<String> strList = new ArrayList();

        Process process;
        process = Runtime.getRuntime().exec(new String[]{"/bin/sh","-c",shStr},null,null);
        InputStreamReader ir = new InputStreamReader(process
                .getInputStream());
        LineNumberReader input = new LineNumberReader(ir);
        String line;
        process.waitFor();
        while ((line = input.readLine()) != null){
            strList.add(line);
        }

        return strList;
    }
}
