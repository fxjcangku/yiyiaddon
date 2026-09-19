package com.yiyiaddon.e.n.e;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;

public final class b {
   private final Path f = Minecraft.getInstance()
      .gameDirectory
      .toPath()
      .resolve(
         (String)com.yiyiaddon.m.b.a<"s8kyh16oqwarp","HQQjCEWuHMUOHsjrxbrpp6mG65Zzwk4BHiaDJ1yJZ58c3hjs7xcV3xGt2ySaI0Br6RY=",-7546975978852273741,-679977175109453352,-8041738454598807312,-8711742773452391002>()
      )
      .resolve(
         (String)com.yiyiaddon.m.b.a<"s3cz5r0w9ilgzk","bToYBldmd5wBwUDKK9Cx2xu5+i8O4Mo4jaT6MZ5YfOU1lRtzYujxXg==",4250906313360341968,7472178944537320860,-4716956522330208187,-7288014814485370188>()
      );

   private Path a(String var1, String var2) {
      String var3 = (var1 + var2)
         .replaceAll(
            (String)com.yiyiaddon.m.b.a<"s150gw4xcrjpis","ZURCF2IgVm+SPQyTDE3niqfWrqSswscstS5aT3JeQt0Kc5xOdx6TUVHrg+3rRWw/R6NTew==",4088625374872791084,-7062176867572106718,6384789403526682658,5048419732147914126>(),
            (String)com.yiyiaddon.m.b.a<"s3np1tv3f8ghq2","BqFcx1azoJUFbranF3eBuDnPD2Wx0I8qqFTLMr00",5967957433813007256,5327118105278741839,7698647865455198785,8848702916719359389>()
         );
      return this.f.resolve(var3 + "");
   }

   public Map<String, a> b(String var1, String var2) {
      LinkedHashMap var3 = new LinkedHashMap();
      Path var4 = this.a(var1, var2);
      if (!Files.isRegularFile(var4)) {
         return var3;
      }

      try {
         JsonObject var5 = JsonParser.parseString(Files.readString(var4, StandardCharsets.UTF_8)).getAsJsonObject();
         if (var5.has(
               (String)com.yiyiaddon.m.b.a<"s3m0gl1p217v5m","AIaM25juXtIXfj4E8XyD5GYM5S7zoZKkW2YBGXE/rPtjpgYN",-6818787614433552398,-4008965577428724379,7689426824707342657,-479088128278906906>()
            )
            && var5.get(
                  (String)com.yiyiaddon.m.b.a<"s3m0gl1p217v5m","AIaM25juXtIXfj4E8XyD5GYM5S7zoZKkW2YBGXE/rPtjpgYN",-6818787614433552398,-4008965577428724379,7689426824707342657,-479088128278906906>()
               )
               .isJsonArray()) {
            for (JsonElement var7 : var5.getAsJsonArray(
               (String)com.yiyiaddon.m.b.a<"s3m0gl1p217v5m","AIaM25juXtIXfj4E8XyD5GYM5S7zoZKkW2YBGXE/rPtjpgYN",-6818787614433552398,-4008965577428724379,7689426824707342657,-479088128278906906>()
            )) {
               if (var7.isJsonObject()) {
                  JsonObject var8 = var7.getAsJsonObject();
                  int var9 = var8.get(
                        (String)com.yiyiaddon.m.b.a<"s2dpu061pm2zbm","76yi2vmstr+D6iuwohsIKc+Ogwv+0MWkOrFGZxjKCUc7HQ==",-3985747713788867916,-7953857010496116725,1694307396461977920,-2691868788609526305>()
                     )
                     .getAsInt();
                  int var10 = var8.get(
                        (String)com.yiyiaddon.m.b.a<"s4vixbxh881wa","EPH3Bc23+0ZWDE9d5n37tZL1nKjG7IznkqiWszcndBzTMg==",5951605705911855875,321890372892446557,281614144343989655,4213461989911439804>()
                     )
                     .getAsInt();
                  int var11 = var8.get(
                        (String)com.yiyiaddon.m.b.a<"sq0ic601vr3w5","rcfG3qtRI6FKJ7njJj9nFgNAeL8Kx3GDwLiDaXunXy23Pg==",-2685746826211519223,9203521379711055794,6682176879319647125,406301363010265194>()
                     )
                     .getAsInt();
                  String var12 = var8.has(
                        (String)com.yiyiaddon.m.b.a<"s35korkt3o20bz","Ut3lkBjfrocFVxudjcrtIrjv9JqlViRlJBqutoNz3U5MNYmT",5779697735419811535,-2000701111676599215,-4105659951538386345,-6690807220070730335>()
                     )
                     ? var8.get(
                           (String)com.yiyiaddon.m.b.a<"s35korkt3o20bz","Ut3lkBjfrocFVxudjcrtIrjv9JqlViRlJBqutoNz3U5MNYmT",5779697735419811535,-2000701111676599215,-4105659951538386345,-6690807220070730335>()
                        )
                        .getAsString()
                     : (String)com.yiyiaddon.m.b.a<"s226gj5sh52olg","9Qri1hCffqqSGkSJcpLeu2Wk7AoaGbQIJv6Y0w==",1986834065022582755,973033556678402873,5405926620258396701,-2037512691685859556>();
                  boolean var13 = !var8.has(
                        (String)com.yiyiaddon.m.b.a<"s3q75h3ns9ph8u","QzCSV/Ng4tXInpnqmQvc+b0tmd7cvbKiJJQ+n9g2tqw=",2043333756730269331,-7282858560170382587,-3192174234298145484,-1381381805916689650>()
                     )
                     || var8.get(
                           (String)com.yiyiaddon.m.b.a<"s3q75h3ns9ph8u","QzCSV/Ng4tXInpnqmQvc+b0tmd7cvbKiJJQ+n9g2tqw=",2043333756730269331,-7282858560170382587,-3192174234298145484,-1381381805916689650>()
                        )
                        .getAsBoolean();
                  a var14 = new a(var9, var10, var11, var12, var13);
                  var3.put(e(var14.a()), var14);
               }
            }
         }
      } catch (Exception var15) {
      }

      return var3;
   }

   public boolean a(String var1, String var2, Map<String, a> var3) {
      Path var4 = this.a(var1, var2);
      JsonObject var5 = new JsonObject();
      var5.addProperty(
         (String)com.yiyiaddon.m.b.a<"s3pm6hmg4uk5xw","FtATah6KRzSDFblpCE8hvDTD7Z+LJPoh8IChx9ooexaQcw==",-5552357807517210330,-6780512950825546597,-3143072393279734670,8993499533128910265>(),
         var1
      );
      var5.addProperty(
         (String)com.yiyiaddon.m.b.a<"s2vdqfigynfqpk","DUWF+sD+vQoZnnAfG7KGaM/Nn+UIfFrv5DP93XM4/PQ=",-5063803433210765928,-4397023440158175815,1607891034221925787,-4625383591866268260>(),
         var2
      );
      JsonArray var6 = new JsonArray();
      Iterator var7 = var3.values().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s216yq8ejmw4m6","RZ48G5JNWbQCPZ9Ey9CNKF5B8IXOdLSO8WeTadxdG/Q=",-6330552566639416918,8284262379918580880,-1816220294517698112,-7600285757494408893>()) {
         case -1910639405:
            while (var7.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"sobmtao5tewa0","BIWrtMlRV8CCK3cetC7xtSgwW3cg8TQTvT1TzEuL4jM=",-2416296909688750069,2071045106614683580,-2880805576237320668,-5638078662056942148>()) {
                  case -880814049:
                     a var8 = (a)var7.next();
                     JsonObject var9 = new JsonObject();
                     var9.addProperty(
                        (String)com.yiyiaddon.m.b.a<"s2dpu061pm2zbm","76yi2vmstr+D6iuwohsIKc+Ogwv+0MWkOrFGZxjKCUc7HQ==",-3985747713788867916,-7953857010496116725,1694307396461977920,-2691868788609526305>(),
                        var8.aj()
                     );
                     var9.addProperty(
                        (String)com.yiyiaddon.m.b.a<"s4vixbxh881wa","EPH3Bc23+0ZWDE9d5n37tZL1nKjG7IznkqiWszcndBzTMg==",5951605705911855875,321890372892446557,281614144343989655,4213461989911439804>(),
                        var8.ak()
                     );
                     var9.addProperty(
                        (String)com.yiyiaddon.m.b.a<"sq0ic601vr3w5","rcfG3qtRI6FKJ7njJj9nFgNAeL8Kx3GDwLiDaXunXy23Pg==",-2685746826211519223,9203521379711055794,6682176879319647125,406301363010265194>(),
                        var8.al()
                     );
                     var9.addProperty(
                        (String)com.yiyiaddon.m.b.a<"s35korkt3o20bz","Ut3lkBjfrocFVxudjcrtIrjv9JqlViRlJBqutoNz3U5MNYmT",5779697735419811535,-2000701111676599215,-4105659951538386345,-6690807220070730335>(),
                        var8.dk()
                     );
                     var9.addProperty(
                        (String)com.yiyiaddon.m.b.a<"s3q75h3ns9ph8u","QzCSV/Ng4tXInpnqmQvc+b0tmd7cvbKiJJQ+n9g2tqw=",2043333756730269331,-7282858560170382587,-3192174234298145484,-1381381805916689650>(),
                        var8.ar()
                     );
                     var6.add(var9);
                     switch ((int)com.yiyiaddon.m.b.a<"s181tggwq07abb","wmfJA3Wz3Y1KV6NDRx4WQrniuiLCWc82X6tG28AiSLs=",4712272744713703433,-5844521946438221332,288364881765539791,-5364696740629357303>()) {
                        case 994925810:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            var5.add(
               (String)com.yiyiaddon.m.b.a<"s3m0gl1p217v5m","AIaM25juXtIXfj4E8XyD5GYM5S7zoZKkW2YBGXE/rPtjpgYN",-6818787614433552398,-4008965577428724379,7689426824707342657,-479088128278906906>(),
               var6
            );
            return com.yiyiaddon.j.a.a(var4, var5);
         default:
            throw null;
      }
   }

   public static String e(BlockPos var0) {
      return "" + var0.getX() + var0.getY() + var0.getZ();
   }

   public a a(String var1, String var2, BlockPos var3) {
      Map var4 = this.b(var1, var2);
      return var4.getOrDefault(e(var3), a.a(var3.getX(), var3.getY(), var3.getZ()));
   }

   public boolean a(String var1, String var2, a var3) {
      Map var4 = this.b(var1, var2);
      var4.put(e(var3.a()), var3);
      return this.a(var1, var2, var4);
   }
}
