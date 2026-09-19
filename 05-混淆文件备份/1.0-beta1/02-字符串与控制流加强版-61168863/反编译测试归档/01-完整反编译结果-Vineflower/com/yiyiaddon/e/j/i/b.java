package com.yiyiaddon.e.j.i;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yiyiaddon.e.j.e.c;
import com.yiyiaddon.e.j.e.d;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;
import net.fabricmc.loader.api.FabricLoader;

public final class b {
   private static final Path c = FabricLoader.getInstance()
      .getConfigDir()
      .resolve(
         (String)com.yiyiaddon.m.b.a<"s17f5bev61gilf","Le6IwfrX516HZx/zNN8+oB/40TMR4pNFmQI5E1ScdA5N97UndSW8yO/ZMu8KqA==",7541960104506110987,-5352328976379937491,-5301847656637048123,2106099153785719612>()
      )
      .resolve(
         (String)com.yiyiaddon.m.b.a<"s2gnu5ptp8g9we","RcwulkmOD9XJKQ6vtF/SnBm9huxTPb6i6dxI4s9yeJ6tbLRRsTDfKA==",7090999202667280083,-4686258575174334034,4413015849946049307,3117002114751361017>()
      )
      .resolve(
         (String)com.yiyiaddon.m.b.a<"s10byy432lab9b","1snFvGgMpO93bDmW42O62jmN3Cj1h0hydlUwx5vfPhxiamuEXq7cxA==",1255628053223649685,4327715210783506811,3206464956197424171,930981561686163776>()
      );
   private static final Path d = FabricLoader.getInstance()
      .getConfigDir()
      .resolve(
         (String)com.yiyiaddon.m.b.a<"s17f5bev61gilf","Le6IwfrX516HZx/zNN8+oB/40TMR4pNFmQI5E1ScdA5N97UndSW8yO/ZMu8KqA==",7541960104506110987,-5352328976379937491,-5301847656637048123,2106099153785719612>()
      )
      .resolve(
         (String)com.yiyiaddon.m.b.a<"sce36ic580i8e","BLt2pNH3yG0Ry02IGwCz1cdPPGNWXo5RJ32+pIOayPU=",-3828612574675452172,6407620956157135431,2191088603180465270,-4099404149463328699>()
      );
   private final Map<d, c> M = new EnumMap<>(d.class);
   private String nc;

   private Path b() {
      return c.resolve(com.yiyiaddon.i.g.c.gn() + "");
   }

   public void C() {
      this.M.clear();
      this.nc = com.yiyiaddon.i.g.c.gn();
      Path var1 = this.b();
      this.c(var1);
      JsonObject var2 = com.yiyiaddon.j.a.a(var1);
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2a6vhw837dmk7","tVWsXEGDOvWbNdVJNSdlVM3xru1y9BuEqj/lVDD0rr4=",5462118835137549761,1049696510231329573,3203623021107618330,4137879998176998186>()) {
            case -3063779:
               return;
            default:
               throw null;
         }
      } else {
         d[] var3 = com.yiyiaddon.e.j.e.d.values();
         int var4 = var3.length;
         int var5 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s3knrvcr76akkm","OHCN94Zn7HFDsnZX65nVKoz9y3oe/kOhJKNqiMkebno=",5546703714809501342,-6319122849367915845,-5137611016808935302,-5248815691727306500>()) {
            case 588188676:
               while (var5 < var4) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3qt2py1jwfta8","UzE4ccvrtljPZYksoVmW93jahwB5kQcFdpJHpP6yoKI=",2683065713943441947,-1615624177729575408,-1040291484996908651,-5948511408388218111>()) {
                     case 294254532:
                        d var6 = var3[var5];
                        JsonElement var7 = var2.get(var6.aM());
                        if (var7 == null) {
                           label37:
                           switch ((int)com.yiyiaddon.m.b.a<"sbjd2frdzu5uu","M6fopFH3jQYkFxgpXB/YZ2G93qxxPfSlqjUYyn7BTGI=",-3482058355949690166,-3103950832961473475,-2133582597572796943,-8478218726983268755>()) {
                              case -1947991804:
                                 switch ((int)com.yiyiaddon.m.b.a<"s2r3moogwuhghe","oc6RMO6qkjqUNFo1RbBNkCjDEkM41q+3348SssF7SZw=",7080998364699043926,6162373139585975877,8961333936404798101,156222114291033066>()) {
                                    case 546175757:
                                       break label37;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           c var8 = a(var7);
                           if (var8 != null) {
                              label33:
                              switch ((int)com.yiyiaddon.m.b.a<"sxo2hs1y90cpc","0L/kaNqnHVWFOH3uMHeda0uv83uFoNJEdhFCRrXvryE=",-6409523912114170799,-735647810372204914,4973205809884671636,8702959131355472702>()) {
                                 case 1643038685:
                                    this.M.put(var6, var8);
                                    switch ((int)com.yiyiaddon.m.b.a<"s1pul0owrgus33","i3w+CrVb2GBf3Y3leWSTi4IEItnACwLlXLEA9KfolLQ=",-1297059965722657723,7104270305316966648,-1615262485739586556,4042808689601059588>()) {
                                       case -1259886394:
                                          break label33;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }
                        }

                        var5++;
                        switch ((int)com.yiyiaddon.m.b.a<"s1ienykin2kipz","QE/2Q+BMg4lRvmJ0WbHen4bLPVWJp6F/eMCv0yw57ks=",417606345956208013,-8459484821165035574,1055533738790987474,6297994688266130401>()) {
                           case -830529940:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return;
            default:
               throw null;
         }
      }
   }

   private static c a(JsonElement var0) {
      if (var0.isJsonObject()) {
         switch ((int)com.yiyiaddon.m.b.a<"slw0300udsr8i","imAJVh+MhshIVytpIeGTEVaLoEOck72gmL8uiOELgww=",2889222273635961118,3308487579312339690,-665846554466337786,4321903710323627645>()) {
            case 1421463716:
               return a(var0.getAsJsonObject());
            default:
               throw null;
         }
      } else {
         if (var0.isJsonArray()) {
            label28:
            switch ((int)com.yiyiaddon.m.b.a<"s25g0nmjegfdr","CmLDVqp1VkiX6L+04VLFKbWQ17HdJKPxVw64sl+NJfs=",2403874595087149033,1113456360701303116,4191935973380788805,7301611736493573380>()) {
               case 1413341794:
                  JsonArray var1 = var0.getAsJsonArray();
                  Iterator var2 = var1.iterator();
                  switch ((int)com.yiyiaddon.m.b.a<"s3qmijm9mr1hxy","eXKgqFYVnOGGKjfCEnSAzrAHt63m2/h2FbA0H0Hdd2A=",1564214870226730640,5943038045172650136,-4834168034211819512,-3436656179049842268>()) {
                     case -1730856457:
                        while (var2.hasNext()) {
                           switch ((int)com.yiyiaddon.m.b.a<"smmvfu62fulm9","65Qgc6Fkui4nzANTEvhwXtGBetBx2cLoDrMRsnqbCDA=",-4613582682889074231,-5624874525625611755,-5141547638862973820,-2704595760537927451>()) {
                              case -2015898321:
                                 JsonElement var3 = (JsonElement)var2.next();
                                 if (var3.isJsonObject()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1razd5vsxqehi","8H9AZc/wEpyxqkkSXMJCQQBzDyz7tYFM63MiVcXpVMA=",-1649502191382429505,407955465823247807,-3004397050661134179,-756366238067107031>()) {
                                       case 785630495:
                                          return a(var3.getAsJsonObject());
                                       default:
                                          throw null;
                                    }
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s3m6c1tuhdzgiy","crZ/HdLKjFa5AQ7/Xa7gPXFAGitj0kCAU+QrBW9Qj58=",8912993070928354804,9140508306759036840,-449136116139714570,1128113280996324496>()) {
                                    case 1379192824:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break label28;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         return null;
      }
   }

   private void cz() {
      if (com.yiyiaddon.i.g.c.gn().equals(this.nc)) {
         switch ((int)com.yiyiaddon.m.b.a<"s1wdqnl5nofnug","AUlu6h/tUHHC5lxSau0wx5JC4EOC7Pqfwk/n9sCKnKw=",3782467229592251164,4245359759109142739,5566664667941674603,-5491550633861635927>()) {
            case -666285181:
               return;
            default:
               throw null;
         }
      } else {
         this.C();
      }
   }

   public c a(d var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s15ke2co91ufoj","AkT8QaulHgQ/p3m8uU+kKIRn8XDp/gIym3u2ntStjhQ=",-5013049321520318029,-4522748635275681610,-72653782635050338,-9138970189724302805>()) {
            case -709265078:
               switch ((int)com.yiyiaddon.m.b.a<"s3qjlmrulxfslh","Z5RjofRybwI9qK4T0OeS5HHHMgOcvixMK3mXYQTLgb8=",592981237642609091,4001745581399204722,-5416200268179995712,-3837760825216720157>()) {
                  case -1930753603:
                     return null;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         c var10000 = this.M.get(var1);
         switch ((int)com.yiyiaddon.m.b.a<"s185noy1p5g2y9","59jWo+587nN2F0Rjq4rgZlKj76crW1C3kjFynxByDJA=",-6311375855112600660,-5580361677201966481,-7978552550544375133,7571517865797352852>()) {
            case -1871726090:
               return var10000;
            default:
               throw null;
         }
      }
   }

   public boolean a(d var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2of436t2gvlmj","1obFp7sUBLCPpC8KkJ0dGNtjC/SW07mcsk8I2oL+k2U=",627847094057631591,5994182585070591867,-7021628951135152709,-7602798125780925606>()) {
            case -821907014:
               if (this.M.containsKey(var1)) {
                  switch ((int)com.yiyiaddon.m.b.a<"sq0hrw8gthnmm","J0fR9eRszNvGbdz++drHYhzFCFUqjL0iXAVsFyLpi20=",5485873074414316062,3402861304847010098,-5312204258820906896,7076781702429108487>()) {
                     case 855505917:
                        switch ((int)com.yiyiaddon.m.b.a<"s1m00zdze163fd","vzZEwrTXm/LqDjTsCj7uW5G+24o1uotomxh91EKXGnE=",-1029370378842480938,-2195602417680275648,-8156444533658963164,1308455435040932948>()) {
                           case 1933960229:
                              return true;
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

      switch ((int)com.yiyiaddon.m.b.a<"s2ghoo602hewrd","RIRLzonlK6zrXCGtjPA/vkewS9azX/ew7xqZ77We+Ds=",-2878422015861985547,-2101678170767779358,-8107567062724031459,4371119307126687229>()) {
         case -823676826:
            return false;
         default:
            throw null;
      }
   }

   public void a(d var1, c var2) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"szx1zjm1q940z","yRCNfR+l/RxHGXnMK6EjCuqfJV4Cc8ieTE9OaeKanzM=",95444456085605738,-763825344720592494,2429406904073337012,-3161280943594453007>()) {
            case -455309988:
               if (var2 != null) {
                  this.cz();
                  this.M.put(var1, var2);
                  this.e();
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s13578xzsamo55","rFBKzKLCNHvqkyqf1I9hHJHmWndimUsKLEsVzf8s4NA=",4164768374562316049,-5215570169692824589,6465692790921296499,-2285694943441611894>()) {
                     case -487428090:
                        return;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      }
   }

   public boolean b(d var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s36cdqwlijncjr","VURW/HprPoBiCirTJnwOnw6FUJekQTevRmLRB6OopRs=",7983612571395326554,-4063997421647259685,6504073676668911282,-7773024304210409076>()) {
            case 2086539246:
               return false;
            default:
               throw null;
         }
      } else {
         this.cz();
         if (this.M.remove(var1) == null) {
            switch ((int)com.yiyiaddon.m.b.a<"siq9q3fl6mu2w","jhy5X+/nd5rSjm0i3Twr7qwThFQsxIBVeYiyZ2qD/+4=",-9178238373230451730,6265458267981178874,6874461507137962533,-3462872513440127506>()) {
               case 1100412559:
                  return false;
               default:
                  throw null;
            }
         } else {
            this.e();
            return true;
         }
      }
   }

   public int an() {
      this.cz();
      int var1 = this.M.size();
      this.M.clear();
      this.e();
      return var1;
   }

   public int a() {
      return this.M.size();
   }

   public void cy() {
      this.M.clear();
      this.nc = null;
   }

   public JsonObject c() {
      JsonObject var1 = new JsonObject();
      d[] var2 = com.yiyiaddon.e.j.e.d.values();
      int var3 = var2.length;
      int var4 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s2x4pllyyi3r17","qj5JzpjFZma/9xYAGssu25mkC0BNvM6bQwth8Fn16Ro=",8740986623199988529,9059562475397094637,-4663044068612588233,-2200744334537825857>()) {
         case 1866477031:
            while (var4 < var3) {
               switch ((int)com.yiyiaddon.m.b.a<"s2drqx7us9btjq","S0BvhHFLM3P5Ii9VNW4a79G0p01p8Hna+i42rvZDi1Q=",2384595058015641834,6470801283672653056,4194767637781399883,6341700923858616741>()) {
                  case -2088735271:
                     d var5 = var2[var4];
                     c var6 = this.M.get(var5);
                     if (var6 == null) {
                        label27:
                        switch ((int)com.yiyiaddon.m.b.a<"s3tno47tsjkek4","il/RKttspCgDTaAuEjpDnq9RCPLXtd/f61t7FiWHrKY=",4536998302418645303,585283734720770119,5412629873998667989,-4632985562722934085>()) {
                           case 847428226:
                              switch ((int)com.yiyiaddon.m.b.a<"s31khzozctttmp","ajqLEK05wpK/Uz7yLfdsfvWvBRCTMnGxKWAET5mf0Nc=",5057775172725093747,2196467427120653833,9028291536927793214,9186194401886066319>()) {
                                 case -895841483:
                                    break label27;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        var1.add(var5.aM(), a(var6));
                        switch ((int)com.yiyiaddon.m.b.a<"s3w0xckllvt54f","Y+fnUNXbihUafV314sawi9Olrtzqok8YVt26jS+Mwds=",-1286455003624233009,8471922566724529977,4716842029974949779,-7459838405178152748>()) {
                           case 758785590:
                              break;
                           default:
                              throw null;
                        }
                     }

                     var4++;
                     switch ((int)com.yiyiaddon.m.b.a<"s1obce8inwhlv6","BOs3wPpWQ8FrcU6JHT6pEhcaW/0BTRAnzVyeZsnj60w=",-9105220787012847940,6756631659166867213,4631807970449347911,-827122366475981239>()) {
                        case 640300469:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return var1;
         default:
            throw null;
      }
   }

   public int a(JsonObject var1) {
      this.cz();
      this.M.clear();
      this.M.putAll(a(var1));
      this.e();
      return this.M.size();
   }

   public static Map<d, c> a(JsonObject var0) {
      EnumMap var1 = new EnumMap<>(d.class);
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2uty3do8eaj1t","+OlAEpvWusceFJmtTJ+OLdk51AfDPNSXBCIszrMtbkE=",-7045794201457175603,365018180341407993,-9006589418726728609,-7966866554917390531>()) {
            case -45643372:
               return var1;
            default:
               throw null;
         }
      } else {
         d[] var2 = com.yiyiaddon.e.j.e.d.values();
         int var3 = var2.length;
         int var4 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s1vsyrv5jnsitl","mQcxNcytE5kNJeHtuW5KV9MLFx68ixj98ctc2OR6StE=",2284375590201731526,-4819039932083541605,-6107930520603480189,-3627386438496994329>()) {
            case -1463462984:
               while (var4 < var3) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3ao6gyv95zwwf","ILqy/nLYmAXfrToIUMIv5evzdaH/iW5g06i5YmonNlc=",-439068977146228602,-6388297409436200306,17846869550680205,8744186790308308414>()) {
                     case 984293379:
                        d var5 = var2[var4];
                        JsonElement var6 = var0.get(var5.aM());
                        if (var6 == null) {
                           label37:
                           switch ((int)com.yiyiaddon.m.b.a<"s2haczhntfagl0","MtLgv3219PdX6SKvuAU/jGSL06Tv2TFOFPreKA38gGQ=",-3821310439734508489,-5776956158802625641,-1363754548180405796,5988603871680380999>()) {
                              case 571549464:
                                 switch ((int)com.yiyiaddon.m.b.a<"sujrdy17jf9l1","VDEnoGrTxzQMxkoCwXycotrJyl7NIWRvdCGuyonb6VI=",1854416568425134674,-7275372341907949974,6940682856516347463,-8514571282650380486>()) {
                                    case 1113664210:
                                       break label37;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           c var7 = a(var6);
                           if (var7 != null) {
                              label33:
                              switch ((int)com.yiyiaddon.m.b.a<"s6jaoxgz293p0","NfNnPN1aiWBkUYpl7GpRbB9I30NIlzF2AFzNiUpB9Fg=",-544102510439811313,8867413223021497381,-1037534980146717970,-5623619041995381575>()) {
                                 case -1053623834:
                                    var1.put(var5, var7);
                                    switch ((int)com.yiyiaddon.m.b.a<"stje3a5g4gnva","M9x4oIulDFE6ErOYdssxdKXBRuyF37hBGkvOKdzrmCQ=",7398223596562209808,7218129881443818799,-2901116705102719612,-822808710512334146>()) {
                                       case 665292988:
                                          break label33;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }
                        }

                        var4++;
                        switch ((int)com.yiyiaddon.m.b.a<"s2k6ocy2txpoje","InuxgBkiCuBs8Wj0hmsxzBlaCehqAnYB8zcy1CthuYI=",-7163334436544369013,-1538486264456346153,-3849663635430239123,-175266194097875366>()) {
                           case -1169490241:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return var1;
            default:
               throw null;
         }
      }
   }

   private void c(Path var1) {
      if (!Files.exists(var1)) {
         String var2 = com.yiyiaddon.i.g.c.go();
         if (var2 != null) {
            Path var3 = d.resolve(var2 + "");
            if (Files.isRegularFile(var3)) {
               try {
                  Files.createDirectories(var1.getParent());
                  Files.copy(var3, var1);
               } catch (Exception var5) {
               }
            }
         }
      }
   }

   private void e() {
      if (this.nc != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sbf4c4zt8m4jm","Tdy2bdUOjp1yzb8dFa+dKQN26/KQjbMruPntMBTaj9M=",668948711520698064,-7229452821159185886,2748852320886360676,-3298854868535964093>()) {
            case -1768873268:
               if (this.nc.equals(com.yiyiaddon.i.g.c.gn())) {
                  JsonObject var1 = new JsonObject();
                  d[] var2 = com.yiyiaddon.e.j.e.d.values();
                  int var3 = var2.length;
                  int var4 = 0;
                  switch ((int)com.yiyiaddon.m.b.a<"s232zgeukyaizu","DF27SceHIgdbKvbbolK/FNT5URpqLbAMhn+QqF2NPRE=",5906785248504195069,8857828011495152637,-3469154052123611328,-7916569999087026221>()) {
                     case 430249513:
                        while (var4 < var3) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3eujb1yv3wkuh","j4mFmEZJtxpJ9qp/sG6GU1RP89N4d6S5HR25wMxHMuw=",6136013026478117676,4653327702613897779,710897603213213125,-548047396209909089>()) {
                              case -10567163:
                                 d var5 = var2[var4];
                                 c var6 = this.M.get(var5);
                                 if (var6 == null) {
                                    label34:
                                    switch ((int)com.yiyiaddon.m.b.a<"s3ice3qe4c6yf1","3KrD23FegVCdEYXorwXQOvanjfH0SYR0HIoXJhnSIwQ=",5578114174410446953,7971003682056134739,5665129654953035078,3695708295783060744>()) {
                                       case -1846668706:
                                          switch ((int)com.yiyiaddon.m.b.a<"s3dvn6hmvroe90","ZiEvItV/ua+jL/kKSwsdhY0VIUKYX/y4+uWxdcR07v8=",2187404596024469196,-7597507588432558527,-305103563116733089,-7732014510658856020>()) {
                                             case -676269661:
                                                break label34;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    var1.add(var5.aM(), a(var6));
                                    switch ((int)com.yiyiaddon.m.b.a<"s1g41zail12qel","OGbnLd2L1ckC7JCAfEuXhyhnBDejlj0X4xMzqFm3BOg=",-8235653948297021900,-3170722664120394977,5097325450169663460,-6799429264737960132>()) {
                                       case 258964913:
                                          break;
                                       default:
                                          throw null;
                                    }
                                 }

                                 var4++;
                                 switch ((int)com.yiyiaddon.m.b.a<"s3ui5qujt9e42m","ifW+OkUdHj4/N9f6awaKfNsjpg9r/4Fy9VQ7BqaJCSU=",1052176477675839450,4376534333147223692,-793993880120192622,1191326332365454278>()) {
                                    case 1294691425:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        com.yiyiaddon.j.a.a(this.b(), var1);
                        return;
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"sqwaefswsgqt","iGnC2UDu8rngjpRRSRKCHrZN91iZlzxhKRoAKDxHzZ8=",-1235126143988987967,-7630742486875256718,-8654718000321797470,-3648870189530187650>()) {
                     case -60159792:
                        return;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      }
   }

   private static JsonObject a(c var0) {
      JsonObject var1 = new JsonObject();
      var1.addProperty(
         (String)com.yiyiaddon.m.b.a<"s3688e6gwiog8m","jKILethLPVe7h85DGlX4yNl5KhEDRAQgTxqIfpuJ",976215563017900096,-901393393745550593,180664209186153861,-6805039698088939904>(),
         var0.aj()
      );
      var1.addProperty(
         (String)com.yiyiaddon.m.b.a<"s1jdbot9u5tj3b","JrBPLhR6j7SxgxPt6GEitB2gfzRKYnPDNGoRIuYn",-3247905330521623418,-7676237219431240816,5569411194822526122,-5385933907054761800>(),
         var0.ak()
      );
      var1.addProperty(
         (String)com.yiyiaddon.m.b.a<"s32rv65o4j16rn","/FpPY8zokME65vE2o+8ySQaXP0fAAwBCpR7Li2H3",-6191228040173487197,1618768183708733978,-4651306785778578690,5538108168673847870>(),
         var0.al()
      );
      String var10001 = (String)com.yiyiaddon.m.b.a<"s2t1n4hvn7mf52","OwZjAgChq1Uj9rffMfT0oQs57E45+bUx24gc5VbX7VlJQRWOWtl4t9WU+/KQyQ==",5423654075360832487,4040289424160153499,-1212774953038921402,1145557435513837815>();
      String var10002;
      if (var0.bU() == null) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"s3o3zjvi6vpq61","6GK9qT5bavDtqYEWbpBuniBlB6FMrBM+0AX9NNK6t8g=",9112283982847904048,4024392528145802253,-8234174201292227919,-4580792510378995812>()) {
            case -1511849295:
               var10002 = (String)com.yiyiaddon.m.b.a<"s17x5n3ce3uhp8","k+TvHBxT8Epg3VtF/h7dhoXJZZnX5uoeIszVlQ==",-8453503401076953258,7335347593828678803,7374126592647099691,-5649263187226811763>();
               switch ((int)com.yiyiaddon.m.b.a<"s1d5v50d9a4axp","baAgf5DUXZYPwni1zgg8wwKH9eTiqOQUJVbjyZWlJEU=",1283617299097384268,1211750844877646865,805653774141978682,-636737318907856862>()) {
                  case 788756090:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10002 = var0.bU();
         switch ((int)com.yiyiaddon.m.b.a<"sl78m57e0vaye","JBbU3QzH+Rnl67lZeVqsD6Sx+WnmncLL7BduN0ZKBnk=",1809105654812168009,-612315383745991475,-6286543094587312509,-517243925981112800>()) {
            case 1406997707:
               break;
            default:
               throw null;
         }
      }

      var1.addProperty(var10001, var10002);
      var1.addProperty(
         (String)com.yiyiaddon.m.b.a<"sr5e767fpgdtv","5lBOeVkn6dFb79GcMg+AM8UoGhrKxV1f8TmLf0WR7ee+EQ==",7616599759269041491,-5025360371254676979,2286239304721055103,6759964010708412591>(),
         var0.g()
      );
      var1.addProperty(
         (String)com.yiyiaddon.m.b.a<"ssi3nogs4qlxd","n+8/EqY+EHgRuh1AnbQssFLamOV9J+JsTFzbr/Hha5e2OoRLCno=",-2910436987808190707,2980861354893107997,1711955312183388278,-690047640432641317>(),
         var0.h()
      );
      return var1;
   }

   private static c a(JsonObject var0) {
      return new c(
         a(
            var0,
            (String)com.yiyiaddon.m.b.a<"s3688e6gwiog8m","jKILethLPVe7h85DGlX4yNl5KhEDRAQgTxqIfpuJ",976215563017900096,-901393393745550593,180664209186153861,-6805039698088939904>()
         ),
         a(
            var0,
            (String)com.yiyiaddon.m.b.a<"s1jdbot9u5tj3b","JrBPLhR6j7SxgxPt6GEitB2gfzRKYnPDNGoRIuYn",-3247905330521623418,-7676237219431240816,5569411194822526122,-5385933907054761800>()
         ),
         a(
            var0,
            (String)com.yiyiaddon.m.b.a<"s32rv65o4j16rn","/FpPY8zokME65vE2o+8ySQaXP0fAAwBCpR7Li2H3",-6191228040173487197,1618768183708733978,-4651306785778578690,5538108168673847870>()
         ),
         ao(
            a(
               var0,
               (String)com.yiyiaddon.m.b.a<"s2t1n4hvn7mf52","OwZjAgChq1Uj9rffMfT0oQs57E45+bUx24gc5VbX7VlJQRWOWtl4t9WU+/KQyQ==",5423654075360832487,4040289424160153499,-1212774953038921402,1145557435513837815>()
            )
         ),
         a(
            var0,
            (String)com.yiyiaddon.m.b.a<"sr5e767fpgdtv","5lBOeVkn6dFb79GcMg+AM8UoGhrKxV1f8TmLf0WR7ee+EQ==",7616599759269041491,-5025360371254676979,2286239304721055103,6759964010708412591>()
         ),
         a(
            var0,
            (String)com.yiyiaddon.m.b.a<"ssi3nogs4qlxd","n+8/EqY+EHgRuh1AnbQssFLamOV9J+JsTFzbr/Hha5e2OoRLCno=",-2910436987808190707,2980861354893107997,1711955312183388278,-690047640432641317>()
         )
      );
   }

   private static String ao(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"so1v4s492s6kn","MtZ8hPVS+MP9mQxLPnvRXVrX0VT1I8CIxxCH1ckXiiI=",-6135351451549132705,-5530236784178868478,8558612563514882345,-8203064631956023096>()) {
            case -1811511018:
               if (!var0.isBlank()) {
                  int var1 = var0.lastIndexOf(47);
                  if (var0.startsWith(
                     (String)com.yiyiaddon.m.b.a<"s2pfweb9b0fkp1","MfvQVM+XCOy0Tr+QlzbWSRkEsT3JtkLD52jORUj6hqRRy+U7Q60kWdUZb3tiN1y8yz3V6w==",-2671711763036295473,-22729188893396786,3899925779899477600,6244513861177565200>()
                  )) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3bzu7jvst6zfi","c+QqKaTrwQRY8eF5PW50V0nKkDiTuLOyocYaKvANVHE=",3686181663307231188,1071436151765062739,7486443092684594127,-269196871388079128>()) {
                        case -1717050206:
                           if (var1 >= 0) {
                              switch ((int)com.yiyiaddon.m.b.a<"sl6kztfy98b5v","0SGpvg1kmDH3eFHH7UPPo/eTDOpDYwU1SojIjB3Ztf8=",240678532259411202,6005862251198011146,-5437552623725476765,-912794388152879679>()) {
                                 case 1302456651:
                                    if (var0.endsWith(
                                       (String)com.yiyiaddon.m.b.a<"s33y6jfgjan1fn","aJahyfdR58RSBOwsBkn0Gv/OY2I/zkCO+sHWsSFy",-5088956265966119524,2291250178465103432,3282581312854571677,5589070069349173392>()
                                    )) {
                                       String var2 = var0.substring(var1 + 1, var0.length() - 1).trim();
                                       if (var2.isEmpty()) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s2c3fbbz8x47kj","jAfO4vWfvdvLNDJWts7vbyKO0CiN+tu8I9kl1VFyxtE=",4265063907149450635,968195990495268469,6226729620506667873,-1158885166718178177>()) {
                                             case 1122248818:
                                                switch ((int)com.yiyiaddon.m.b.a<"s1onxk5str8fwr","8ORPdJ/a8Jdam74f0NWv2Kflvkir/XRK88P3MK+sC3M=",-7822479619227501840,-2759988878633380527,5716860347701160766,2149166191824964281>()) {
                                                   case 1288029540:
                                                      return var0;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       } else {
                                          switch ((int)com.yiyiaddon.m.b.a<"s3i40mn4g643mi","9pqm2LyEZME+WbooNIVK/r8dFAqmPzSaA54liFAY5GI=",1087611966339975174,7761188907892849011,8513558021479166625,5468910047061199902>()) {
                                             case 941229621:
                                                return var2;
                                             default:
                                                throw null;
                                          }
                                       }
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"susn5p4cswfj6","QsjbAmqouM5b2Hdvcqf/uDXCStWO+2jLe5xK08XLZyc=",-7538941832156891692,143315956293218036,-1235330446550314851,4984806588565545947>()) {
                                       case -1013686792:
                                          return var0;
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

                  return var0;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s27op6m6zg6suc","OmsyeSIB4b7av1+AVNPTVBVyovSbUBWOj7O16a6nyM8=",1150580169408349831,7956849012454607537,1659652926595014654,-4830965648728570370>()) {
                     case -1707614798:
                        return var0;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return var0;
      }
   }

   private static int a(JsonObject var0, String var1) {
      JsonElement var2 = var0.get(var1);
      if (var2 != null && !var2.isJsonNull()) {
         try {
            return var2.getAsInt();
         } catch (Exception var4) {
            return 0;
         }
      } else {
         return 0;
      }
   }

   private static float a(JsonObject var0, String var1) {
      JsonElement var2 = var0.get(var1);
      if (var2 != null && !var2.isJsonNull()) {
         try {
            return var2.getAsFloat();
         } catch (Exception var4) {
            return 0.0F;
         }
      } else {
         return 0.0F;
      }
   }

   private static String a(JsonObject var0, String var1) {
      JsonElement var2 = var0.get(var1);
      if (var2 != null && !var2.isJsonNull()) {
         try {
            return var2.getAsString();
         } catch (Exception var4) {
            return (String)com.yiyiaddon.m.b.a<"s17x5n3ce3uhp8","k+TvHBxT8Epg3VtF/h7dhoXJZZnX5uoeIszVlQ==",-8453503401076953258,7335347593828678803,7374126592647099691,-5649263187226811763>();
         }
      } else {
         return (String)com.yiyiaddon.m.b.a<"s17x5n3ce3uhp8","k+TvHBxT8Epg3VtF/h7dhoXJZZnX5uoeIszVlQ==",-8453503401076953258,7335347593828678803,7374126592647099691,-5649263187226811763>();
      }
   }
}
