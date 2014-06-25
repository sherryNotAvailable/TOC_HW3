import java.io.*;
import java.net.URL;
import org.json.*;
import java.util.Map;
import java.util.regex.*;
import java.lang.*;
import java.nio.channels.*;
public class TocHw3
{
    public static void main(String[] args) throws JSONException, IOException
    {
        URL dataURL = new URL(args[0] + "?selector=鄉鎮市區=" + args[1] + "%20AND%20交易年月>=" + args[3] + "00&fields=%E7%B8%BD%E5%83%B9%E5%85%83,%E4%BA%A4%E6%98%93%E5%B9%B4%E6%9C%88,%E5%9C%9F%E5%9C%B0%E5%8D%80%E6%AE%B5%E4%BD%8D%E7%BD%AE%E6%88%96%E5%BB%BA%E7%89%A9%E5%8D%80%E9%96%80%E7%89%8C,%E9%84%89%E9%8E%AE%E5%B8%82%E5%8D%80");
        ReadableByteChannel rbc = Channels.newChannel(dataURL.openStream());

        BufferedReader in = new BufferedReader(new InputStreamReader(dataURL.openStream()));  //open URL
        String inputLine;
        int n=10;
        while ((inputLine = in.readLine()) != null) {
                if(inputLine=="土地區段位置或建物區門牌"){
                        n=0;
                }
          //  System.out.println(n+" :  "+inputLine);
        }
        in.close(); //close

        FileOutputStream fout = new FileOutputStream("data.json");
                                                 //Json format
        fout.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        JSONArray data = new JSONArray(new JSONTokener(new FileInputStream("data.json")));
                                                 //read in with json format
        Pattern pt = Pattern.compile(args[2]);   //consider 2nd argument.
        int sum = 0;  //as price sum
        int num = 0;  //data number
        int i;
        for(i=0;i< data.length();i++)
        {
                if(pt.matcher(data.getJSONObject(i).getString("土地區段位置或建物區門牌")).find())
                {                       //第i個objext裡的"土地區段"項目的value
                        num++;
                        sum = sum + data.getJSONObject(i).getInt("總價元");
                }
        }
        sum/=num;     //average price => ANSWER
        System.out.println(sum);
    }
}
                                                                                  
