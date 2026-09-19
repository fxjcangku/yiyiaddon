package com.yiyiaddon.e.n.i;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;

public final class m {
   private final Path j = Minecraft.getInstance()
      .gameDirectory
      .toPath()
      .resolve(
         (String)com.yiyiaddon.m.b.a<"s3c5gsad803nrl","V/5lI68Zk+A4Rmop53YGQRsltg9bKRVIfiNjMI2X9jOlk8XRqW4qvRiJwNUQJJC5764=",-1062810753281685964,8151825810830485549,8412693165689934390,-2112538403043008810>()
      )
      .resolve(
         (String)com.yiyiaddon.m.b.a<"sm29ss9235yum","1hgMy2ptwbIGlHuYlZ+gF2gjeSJX4xr98RfMy93EhfOBcMu8qcsCCt2Srtvfs8Ohwq5GDfIP",-6157620323963399555,-2364242697785451320,-9119358553257241299,-100461189272589565>()
      );

   public Map<String, l> b(String var1, String var2) {
      LinkedHashMap var3 = new LinkedHashMap();
      Path var4 = this.a(var1, var2);
      if (var4 != null && Files.isRegularFile(var4)) {
         try {
            JsonObject var5 = JsonParser.parseString(Files.readString(var4, StandardCharsets.UTF_8)).getAsJsonObject();
            if (!var1.equals(
                  c(
                     var5,
                     (String)com.yiyiaddon.m.b.a<"s3tfh607twzimx","6twqkhwhexH4FwXjCqKcJCdED3QUcvPGGRdHbRX1W4OGEA==",-7449859393041483529,-6056958170174508674,308871523762389614,-9199930343800711237>()
                  )
               )
               || !var2.equals(
                  c(
                     var5,
                     (String)com.yiyiaddon.m.b.a<"s3r0a9nwz8z5tk","NHpGA43vNOSTyWUmJeT/f16/9T0ofRgv173uPdys3oD1ZIVY",3311312093514401396,-3799984984974750755,4027893729400479951,5882436429166776631>()
                  )
               )
               || !(String)com.yiyiaddon.m.b.a<"s2xl81ys6jldhb","4wwqaA9Jysdfmdl8w+Pppn5ZDcwfcjfnQkejLT+h1cfCzZ5pMHD2wnX6usqvD/SJJazcyfERQQ5mXUu6",1431127057396370978,6661428980290982966,684817079295400057,-6328931850241347757>()
                  .equals(
                     c(
                        var5,
                        (String)com.yiyiaddon.m.b.a<"s29s7aa447wn5i","5moraqpDeTDigwUiHIbEngy0Ok0j9EpDIyoEqzcuiWqx9/qFYBVMIw==",-6251611746971393205,1325853047270095479,5333321224300355051,-2019396382919631049>()
                     )
                  )) {
               return var3;
            }

            if (!var5.has(
                  (String)com.yiyiaddon.m.b.a<"s3dww3hrgtydx4","/3IkbwBLNTnYp5X7e0u5rWYctZwrEYeurwYWciuyv8n2yI8p",7786935871997436910,-3700073521504232285,5944236953622843109,-8564424232029426329>()
               )
               || !var5.get(
                     (String)com.yiyiaddon.m.b.a<"s3dww3hrgtydx4","/3IkbwBLNTnYp5X7e0u5rWYctZwrEYeurwYWciuyv8n2yI8p",7786935871997436910,-3700073521504232285,5944236953622843109,-8564424232029426329>()
                  )
                  .isJsonObject()) {
               return var3;
            }

            for (Entry var7 : var5.getAsJsonObject(
                  (String)com.yiyiaddon.m.b.a<"s3dww3hrgtydx4","/3IkbwBLNTnYp5X7e0u5rWYctZwrEYeurwYWciuyv8n2yI8p",7786935871997436910,-3700073521504232285,5944236953622843109,-8564424232029426329>()
               )
               .entrySet()) {
               if (((JsonElement)var7.getValue()).isJsonObject()) {
                  l var8 = a(((JsonElement)var7.getValue()).getAsJsonObject());
                  if (var8 != null) {
                     var3.put((String)var7.getKey(), var8);
                  }
               }
            }
         } catch (Exception var9) {
         }

         return var3;
      } else {
         return var3;
      }
   }

   public boolean a(String var1, String var2, Map<String, l> var3) {
      Path var4 = this.a(var1, var2);
      if (var4 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3tt6jsev3dqj5","XhuekmfmFElGhv796iSTwDbF+zdixY12CpSQp1sdvto=",-6108329618850521469,-7137792121857324760,2874189760813935565,6557700536690580550>()) {
            case -1997682094:
               return false;
            default:
               throw null;
         }
      } else {
         JsonObject var5 = new JsonObject();
         var5.addProperty(
            (String)com.yiyiaddon.m.b.a<"s3tfh607twzimx","6twqkhwhexH4FwXjCqKcJCdED3QUcvPGGRdHbRX1W4OGEA==",-7449859393041483529,-6056958170174508674,308871523762389614,-9199930343800711237>(),
            var1
         );
         var5.addProperty(
            (String)com.yiyiaddon.m.b.a<"s3r0a9nwz8z5tk","NHpGA43vNOSTyWUmJeT/f16/9T0ofRgv173uPdys3oD1ZIVY",3311312093514401396,-3799984984974750755,4027893729400479951,5882436429166776631>(),
            var2
         );
         var5.addProperty(
            (String)com.yiyiaddon.m.b.a<"s29s7aa447wn5i","5moraqpDeTDigwUiHIbEngy0Ok0j9EpDIyoEqzcuiWqx9/qFYBVMIw==",-6251611746971393205,1325853047270095479,5333321224300355051,-2019396382919631049>(),
            (String)com.yiyiaddon.m.b.a<"s2xl81ys6jldhb","4wwqaA9Jysdfmdl8w+Pppn5ZDcwfcjfnQkejLT+h1cfCzZ5pMHD2wnX6usqvD/SJJazcyfERQQ5mXUu6",1431127057396370978,6661428980290982966,684817079295400057,-6328931850241347757>()
         );
         JsonObject var6 = new JsonObject();
         if (var3 != null) {
            label34:
            switch ((int)com.yiyiaddon.m.b.a<"s3ips42jrarhgk","w86cpR63LRLOeH6d/YYANdQRQet8GDeeyAD3xdFRl8c=",-3441948516045822700,6055624834659328288,7399805773165186714,-2471303341590879951>()) {
               case 515809481:
                  Iterator var7 = var3.entrySet().iterator();
                  switch ((int)com.yiyiaddon.m.b.a<"s25cjuzjg1wmow","BKgoeEL3l6B3VzRc+exCoizQQYBIRA7XmKjBLTjpLyo=",-7519831523362005140,1078312143073592444,-3118464461831854308,2880398615433509639>()) {
                     case 1061244546:
                        while (var7.hasNext()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s209lc3xq4el67","3bszxvKikFD47JdbKc6JQZZckEptABhtTwMn4XNLmNQ=",230643234846356754,-4701000653662851940,2182813108862361523,-453134381474152483>()) {
                              case 2053609617:
                                 Entry var8 = (Entry)var7.next();
                                 if (var8.getKey() != null) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s3hs79zolz2w9g","+pODjjcR0/7xRy2RRaTnTuTaOcdh56HOXvFIQ+9m2/U=",3440750957568422489,-2538252998178409913,-868722913487816529,1617638240035661804>()) {
                                       case -1354252117:
                                          if (!((String)var8.getKey()).isBlank()) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s2w14pp6lvo8gw","HNBGzkcf4x1/HO50W4FdJP5jQ5y6Nf+El7ablVuVHcU=",1250734184873132203,-4532313327175455468,347203647105916595,5963147477345619383>()) {
                                                case -709360706:
                                                   if (var8.getValue() == null) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"svwj9tliqh3ne","NW1aNT4cPugH1TzDjLSVSs9r32JrdKZh7iM12PyKuAk=",444565468847127919,-5599950307508989541,-4949351331481179700,6706678397837374086>()) {
                                                         case -545193245:
                                                            switch ((int)com.yiyiaddon.m.b.a<"svrwo7lk35mo9","5rVRNz09iGiwboMi/sJ1bTy9EoEn0hEn8Epd+6ssa2I=",-2940629077860656310,6037877933655002630,4575931798631155875,-3664025490820273138>()) {
                                                               case -784755047:
                                                                  continue;
                                                               default:
                                                                  throw null;
                                                            }
                                                         default:
                                                            throw null;
                                                      }
                                                   } else {
                                                      var6.add((String)var8.getKey(), a((l)var8.getValue()));
                                                      switch ((int)com.yiyiaddon.m.b.a<"s2f459aafee9x5","nvyQOugJ4N+6ArKBNZx3SpkIV/UJhgv5ldHy/sHOR74=",6775492254806283407,-2920199681845559503,6459025735652509052,5578459434639220345>()) {
                                                         case 2061759642:
                                                            continue;
                                                         default:
                                                            throw null;
                                                      }
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          }
                                          break;
                                       default:
                                          throw null;
                                    }
                                 }
                                 break;
                              default:
                                 throw null;
                           }
                        }
                        break label34;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         var5.add(
            (String)com.yiyiaddon.m.b.a<"s3dww3hrgtydx4","/3IkbwBLNTnYp5X7e0u5rWYctZwrEYeurwYWciuyv8n2yI8p",7786935871997436910,-3700073521504232285,5944236953622843109,-8564424232029426329>(),
            var6
         );
         var5.addProperty(
            (String)com.yiyiaddon.m.b.a<"s34h0no95gl7aq","XOKdtDy4/8DndHONxHDzbnkKa2sSIFpr89df+M48gWQ=",-4547184679324493109,-9025313923974067636,-5258604069995776530,1793961545145602540>(),
            1
         );
         return com.yiyiaddon.j.a.a(var4, var5);
      }
   }

   private Path a(String var1, String var2) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ht4rdr5jqysk","pRLIFlTAVi2N3m2COSRRY89EjTvmdeeaZ8qoo/AOmfs=",-2610316597078186028,2354922778318435937,3599049803883050266,-8242863809944165009>()) {
            case 458363012:
               if (!var1.isBlank()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1x7rphaqclm6t","0hiKZ2VdiciJDHArikhc3iIJgYPMmLOExrUjdPBkxY8=",6417334325873819898,-7114011439727003770,-2838448363362793388,8543537318879061113>()) {
                     case -1182731091:
                        if (var2 != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1hebcl1vdmhy2","xR7qbqE9l6WuNY8yn3grKvubsgmEYWn1DDUlWLF156Y=",-2202207696249629711,-8809145952425581125,-8923386660549366119,-4755536691731905727>()) {
                              case -1259133973:
                                 if (!var2.isBlank()) {
                                    return this.j.resolve(g(var1)).resolve(g(var2) + "");
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"st3xjzsc94c0h","2RVfgA6YjOrXsVDKukf9wm9+ojMqH+CITJMcXiO6Lhg=",5203362942479218665,-722473339679264209,6943089024756909360,8138505718694316060>()) {
                                    case -235741001:
                                       return null;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      return null;
   }

   private static String g(String var0) {
      return var0.replaceAll(
         (String)com.yiyiaddon.m.b.a<"s1htgryjrlbgk5","mwEGZXWSV5PUF5kW76QdeatvVOu0qiz4962LZF5hirf9edSBDraYlZtpew6Ehh/Joc/urQ==",6347673725550421847,-321386948612144297,-4861715957806668388,-6772701166978498041>(),
         (String)com.yiyiaddon.m.b.a<"s2pz0xg2q218w5","A9EMcTZVyD+muHpcTogpXMIlQ267DuJA/6odNzPT",-4337066307647264926,585585779421846813,-8848079450861270839,-4719322268983667552>()
      );
   }

   private static JsonObject a(l var0) {
      JsonObject var1 = new JsonObject();
      var1.addProperty(
         (String)com.yiyiaddon.m.b.a<"s1lfblce81r82d","qTzn5CFNwIMtkE1S9OYVb9/EGgfeg5gffd6h7mtAnrevH6Fp",7615484882085407836,2275576101024586524,-5388242267634331132,4968260523761829805>(),
         var0.dK()
      );
      String var10001 = (String)com.yiyiaddon.m.b.a<"snhti58g7etqb","LNK8GItOlvnRw9tVr8SqbIKUet1ty1tVOG7dwp5Z6de/PEM8",7480286619697051703,-4288828386847492406,4854200608889171978,4737473853832618377>();
      String var10002;
      if (var0.a() == null) {
         label53:
         switch ((int)com.yiyiaddon.m.b.a<"s28jdh3dn5lyd8","lenc6ThTpKDB2yRNcihBNr1CN/KbqtrS0Aa8KaXV5Gs=",-9153106054413932295,-3627115728262086500,-9205261349230702358,-5006575223890053987>()) {
            case 1282441262:
               var10002 = null;
               switch ((int)com.yiyiaddon.m.b.a<"s1m0b4guk22ei7","pIGhjX1R2iu3MtdS8D0ZVPOfcqPLDCmv8emYf+jFWOw=",4791421435967645105,-7175048625950361065,-5304985553184552883,-2912100680694751359>()) {
                  case 1290202075:
                     break label53;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10002 = var0.a().name();
         switch ((int)com.yiyiaddon.m.b.a<"s1nvit9xv68dti","lh6HM1aIKn7tGaTv4jXSzTYuM78UZYjTfeNGBr/F56Q=",8696420311973541547,-573932938963041357,-7935375578615303723,-7089802962734986523>()) {
            case -1318507775:
               break;
            default:
               throw null;
         }
      }

      var1.addProperty(var10001, var10002);
      var10001 = (String)com.yiyiaddon.m.b.a<"ssxnnittm1n3m","yHH0XUSOBK6O5OP5pVzsheXmljhzK5AB1UwShfoc7auh0PT6",-5744773559501426174,2697002788256385202,8807229323937497936,6491888995234040944>();
      if (var0.a() == null) {
         label46:
         switch ((int)com.yiyiaddon.m.b.a<"s1ewe2sdwb4tj0","mDPwJaS+L1zvWjyul561WTA9/MPWk+P7P7qACArqHjA=",-7173940520135601975,-390245863474066254,679234611297250472,742340238518147859>()) {
            case 128432344:
               var10002 = null;
               switch ((int)com.yiyiaddon.m.b.a<"s3oivi7yonpuy2","k4vWT1K0xtjev1XTb0F7t6yYPjwUXfqA85SE0Rdp5xY=",4566113492029810165,6913862288047234339,946055104809289059,2636309887432809402>()) {
                  case 629428791:
                     break label46;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10002 = var0.a().name();
         switch ((int)com.yiyiaddon.m.b.a<"s1w8vwxmkbcg89","aP4LPN79EerYOtJ8QPW/4WqVZlnZhP2Yo75PZ1ma+Nk=",6103126453945370577,2913355949984712988,-846251505871994654,-8623725550107806008>()) {
            case -1202626657:
               break;
            default:
               throw null;
         }
      }

      var1.addProperty(var10001, var10002);
      if (var0.dL() != null) {
         label41:
         switch ((int)com.yiyiaddon.m.b.a<"s2y1rl7f9qq459","k9F7/EmT33Io/zKHc3AviaVjjKc8SoNX5sAOYw+ivD0=",48568203105191567,-3948406109306119337,3720365600400269993,4310018140815997341>()) {
            case 347251798:
               var1.addProperty(
                  (String)com.yiyiaddon.m.b.a<"s1j4nx4pwb11dm","HDnjlG/i//zD0Vrt7T10Ndte3ID0elqUNjanx53GFQvc4nZ05/U=",-8037691911927989208,7677222827999634681,-3424532536000485783,1457441007293692822>(),
                  var0.dL()
               );
               switch ((int)com.yiyiaddon.m.b.a<"s16qnytdri1po7","huPyfLhBvJXMgokl81li1So3KX/KfaZTpnt6URY7rmY=",149077799119492101,-5464945295675654154,7750984142160001413,-4877279362881535905>()) {
                  case 535129130:
                     break label41;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      var10001 = (String)com.yiyiaddon.m.b.a<"s2jt5ohnepwn4e","bOhXX2N22tY3OzZXS5YArLy0AiT5o5EYsGtEsVc0fDw=",-8209312445246898286,-8880649657238102224,7170158952912795662,8932106421771910067>();
      if (var0.b() == null) {
         label34:
         switch ((int)com.yiyiaddon.m.b.a<"snnu2a9xcnn04","AOKYDAouweppjTEf1Wbbf008zM+8WTLDD2jZ1vc827I=",-8346940672384841239,-5672337522175805807,608877456223138883,-1374432643438405806>()) {
            case -586919305:
               var10002 = c.UNKNOWN.name();
               switch ((int)com.yiyiaddon.m.b.a<"s1b6btlptsp9bf","CFfLR9a0uKMXIWi3StUJkqA8cpkRNN2w/yO0e2KmOcs=",-6865203707217814738,6286134728626378806,6415706003976999441,7554625819436219105>()) {
                  case 165615176:
                     break label34;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10002 = var0.b().name();
         switch ((int)com.yiyiaddon.m.b.a<"s2lyfbli7au0l3","AMrwIrWh2XPWLiQKpGmOPJISpxYeF8VP/1Sg2rUtd8k=",-7160798257429759848,-5549113975201668630,-530795787345990509,217386196678138593>()) {
            case -1827837586:
               break;
            default:
               throw null;
         }
      }

      var1.addProperty(var10001, var10002);
      var1.addProperty(
         (String)com.yiyiaddon.m.b.a<"s1gojl8k3ob8p3","yaIbZqOyVgBy0NTaPX7bt8yS2QhrF/d/lSGNqiXMIZmcPRJ9aJ6XEA==",5126681605418514918,8725935718293929964,-2811382594701331575,841486504803022539>(),
         var0.dM()
      );
      return var1;
   }

   private static l a(JsonObject var0) {
      try {
         String var1 = c(
            var0,
            (String)com.yiyiaddon.m.b.a<"s1lfblce81r82d","qTzn5CFNwIMtkE1S9OYVb9/EGgfeg5gffd6h7mtAnrevH6Fp",7615484882085407836,2275576101024586524,-5388242267634331132,4968260523761829805>()
         );
         String var2 = c(
            var0,
            (String)com.yiyiaddon.m.b.a<"snhti58g7etqb","LNK8GItOlvnRw9tVr8SqbIKUet1ty1tVOG7dwp5Z6de/PEM8",7480286619697051703,-4288828386847492406,4854200608889171978,4737473853832618377>()
         );
         String var3 = c(
            var0,
            (String)com.yiyiaddon.m.b.a<"ssxnnittm1n3m","yHH0XUSOBK6O5OP5pVzsheXmljhzK5AB1UwShfoc7auh0PT6",-5744773559501426174,2697002788256385202,8807229323937497936,6491888995234040944>()
         );
         String var4 = c(
            var0,
            (String)com.yiyiaddon.m.b.a<"s2jt5ohnepwn4e","bOhXX2N22tY3OzZXS5YArLy0AiT5o5EYsGtEsVc0fDw=",-8209312445246898286,-8880649657238102224,7170158952912795662,8932106421771910067>()
         );
         String var5 = c(
            var0,
            (String)com.yiyiaddon.m.b.a<"s1gojl8k3ob8p3","yaIbZqOyVgBy0NTaPX7bt8yS2QhrF/d/lSGNqiXMIZmcPRJ9aJ6XEA==",5126681605418514918,8725935718293929964,-2811382594701331575,841486504803022539>()
         );
         if (var1 != null && var5 != null) {
            k var6 = var2 == null ? k.RIGHT_CLICK : k.valueOf(var2);
            g var7 = var3 == null ? g.UNKNOWN : g.valueOf(var3);
            c var8 = var4 == null ? c.UNKNOWN : c.valueOf(var4);
            return new l(
               var1,
               var6,
               var7,
               c(
                  var0,
                  (String)com.yiyiaddon.m.b.a<"s1j4nx4pwb11dm","HDnjlG/i//zD0Vrt7T10Ndte3ID0elqUNjanx53GFQvc4nZ05/U=",-8037691911927989208,7677222827999634681,-3424532536000485783,1457441007293692822>()
               ),
               var8,
               var5
            );
         } else {
            return null;
         }
      } catch (Exception var9) {
         return null;
      }
   }

   private static String c(JsonObject var0, String var1) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3e0jc30w8921d","BuWgBz7K/DcmHTd6d5yoeiGbVMqd+mzuDZycbgDNB3Q=",-1338225964913860811,1518437495094653690,-4185138782838095085,4111997229116696173>()) {
            case -273764922:
               if (var0.has(var1)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s34kq1dspwbbjk","0NVwLr/S/sNv993X0+fpW8UPWTaQlvMzwM9HhGOwbZ8=",659959452563275007,-7997098376021316781,-8521543286338914049,2507509136378211480>()) {
                     case -248684482:
                        if (!var0.get(var1).isJsonNull()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1zrdnr4783qmc","gTXCltqdPi89k9C0HrTcMfZQGQxY30OJCw1oFwPuUvM=",3037084859628950749,-3936309903637868876,1725072502729697964,-2341715238552862857>()) {
                              case 265865019:
                                 if (var0.get(var1).isJsonPrimitive()) {
                                    String var2 = var0.get(var1).getAsString();
                                    if (var2 != null) {
                                       label35:
                                       switch ((int)com.yiyiaddon.m.b.a<"ssp4hu0dpqqpr","h591X26ygpc5WT8YObiUArYfWpveuzM5SBQ0Fpx9UXs=",-8291127293702574842,-2098805990590804764,-66893889593697832,2316799771606266492>()) {
                                          case -1886972202:
                                             if (!var2.isBlank()) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s1as32zl7xgzng","5C4nE0fp0m3gAnTuHd9qoA3jZg+7AQQv+6g3VrKFNMw=",-5127133379698276270,7445873770206125165,2668821815039494761,-4080952905954539390>()) {
                                                   case -1555159560:
                                                      return var2;
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             switch ((int)com.yiyiaddon.m.b.a<"s33dc88fr2de9f","0Jb3+GFyg1kV+/uEBZmgs5Byr6va9ZVrRq4qG9tMtCs=",-4363974906004835525,-3471375358457442208,6098590265553923062,1923635107676179848>()) {
                                                case -1597171458:
                                                   break label35;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"sjm1qpuhn8fju","3lu+LDHE01s9xkDa+5ITKtOUoYS1a1GM6ayXzpUtJV4=",4594142881696315879,-8535541873927251550,7407944813890798,9183748455528234623>()) {
                                       case -1721400444:
                                          return null;
                                       default:
                                          throw null;
                                    }
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s3eza0c9uadiq7","8+GX+RD8gX15fXWcRXoeXGZonnQ+Z6CDrPBRrbbLkNo=",3021060560683339892,516089180233789173,-7677923909845697340,2582973246390320064>()) {
                                    case 2065071196:
                                       return null;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      return null;
   }
}
