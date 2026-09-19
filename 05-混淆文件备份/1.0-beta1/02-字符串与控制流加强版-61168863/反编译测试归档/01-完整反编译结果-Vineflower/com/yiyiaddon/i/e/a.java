package com.yiyiaddon.i.e;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Map.Entry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.locale.Language;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;

public final class a {
   private a() {
   }

   public static void x(boolean var0) {
      d.fr = var0;
   }

   public static com.yiyiaddon.g.d.a b(BlockState var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s9zwjrkkuclfn","0zJC2jjozywcCxMrLn6tezDrLx5psqqcLz0SNYsSmog=",-8843746683720201257,6513963994592647250,-4616118907194945907,1194029128131039374>()) {
            case -693461538:
               return com.yiyiaddon.g.d.a.a();
            default:
               throw null;
         }
      } else {
         Identifier var1 = BuiltInRegistries.BLOCK.getKey(var0.getBlock());
         if (var1 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s3ktpnbjqlezka","aETkV62f0ueRf2fSs1KLqFP0h6zYVM2BZ4K6e9HJWCw=",-4702939816805902679,2996064790206672883,-3800569916940825289,-1198971316793443058>()) {
               case -1796279031:
                  return com.yiyiaddon.g.d.a.a();
               default:
                  throw null;
            }
         } else {
            d.bi("" + var1 + var0);
            Identifier var2 = Identifier.tryBuild(var1.getNamespace(), var1.getPath() + "");
            d.a var3 = d.a(var2);
            if (!var3.b()) {
               switch ((int)com.yiyiaddon.m.b.a<"s1i1yy3aryremc","pzbl529NwioAs8wWAE9wK4NFEiI9LeDVGqsBEvlDkuE=",-7993280420550394834,4248672420765326565,3931030187819809198,-37690400609880681>()) {
                  case -1089726733:
                     return a(
                        var1,
                        null,
                        (String)com.yiyiaddon.m.b.a<"s19s8tsdmdepq5","TP2RYuEdhxXtc3D4cOQ9GB6Ij7wckw/MwsYlkOYEjok=",4435241675159313472,1435075750801957811,3451839741843367220,-3865650764743286158>(),
                        var3.br()
                     );
                  default:
                     throw null;
               }
            } else {
               JsonObject var4 = var3.a();
               JsonElement var5 = var4.get(
                  (String)com.yiyiaddon.m.b.a<"shau9l5ee9vpt","o6mnOFgfpHWZjd2m8XH5gZCufOC7NG/E25UhO49b9nOMiakBbor6/440ems=",8871848146917821847,-2998446602100404337,-4658752373061873486,-6560448579026210633>()
               );
               if (var5 != null) {
                  label74:
                  switch ((int)com.yiyiaddon.m.b.a<"s1jrbc00pn5y2y","buKKvkjmQsZfUYIc9tym6nPP1rci5jdQnqm/Y43QXEk=",-5617880442724595859,8567168739006434417,9216874401195876745,-2866841883479703075>()) {
                     case -1572994284:
                        if (var5.isJsonObject()) {
                           List var10 = a(var0, var5.getAsJsonObject()).stream().distinct().toList();
                           if (var10.isEmpty()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3o34fp1jzwmjc","mFijvEc9TRUFd8P2pk2qwH2dS+ksfheDsOny5BVtpEo=",1881539865060212612,3964595598943948139,2523961869917520789,-7676736949292668834>()) {
                                 case -47373395:
                                    return a(
                                       var1,
                                       null,
                                       (String)com.yiyiaddon.m.b.a<"s19s8tsdmdepq5","TP2RYuEdhxXtc3D4cOQ9GB6Ij7wckw/MwsYlkOYEjok=",4435241675159313472,1435075750801957811,3451839741843367220,-3865650764743286158>(),
                                       (String)com.yiyiaddon.m.b.a<"s242e4h0leda29","lADec7EVcPEkmpW4ljFEvtOMhoXerd6hdWK5FFF//4T/1kxN3JOlxcrPmXZWy778lBOqTb/H+IpBDScqjzAyEiFExLA=",-7706415743874514398,7865392810700327403,-6928622429770938995,3863452943819656893>()
                                    );
                                 default:
                                    throw null;
                              }
                           }

                           String var11 = String.join(
                              (String)com.yiyiaddon.m.b.a<"s1exgimmkr3qu1","sL8diY66dDuGm873BeiGCBzvpuf+C3MuQ8xblfzBjJ1jcg==",389017294146780266,5110932305132546490,3377587998812406732,8427325683315550509>(),
                              var10
                           );
                           if (var10.size() > 1) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2wvctwyl96rmc","Sjl1pd4laT7mKoPReYsiyhyLA7c0yNSfpK6gW+T4Hq4=",-2044691561668895896,9205768443486720305,960009270526877444,5561979383792896370>()) {
                                 case -602957861:
                                    return new com.yiyiaddon.g.d.a(
                                       var11,
                                       null,
                                       null,
                                       (String)com.yiyiaddon.m.b.a<"sdnxtnr4em93c","dgH6xi6h7NYqQwSo7BDMfcofAaBa0TWPT2dZHFSgxYs5+Cr4z78=",-7470036329857968099,-3301356464402912291,3843615311164843931,-3568060009074446090>(),
                                       (String)com.yiyiaddon.m.b.a<"s1vo4n6qcmoz1f","+CqXS2XsdOa2moPgKkr14B0lajecjEKvpwN0y9C7dsY=",4852707851154647459,155834380537038788,-2384283934954002740,-8682366650339539741>(),
                                       null
                                    );
                                 default:
                                    throw null;
                              }
                           }

                           String var8 = (String)var10.get(0);
                           com.yiyiaddon.g.d.a var9 = a(var8, var1);
                           if (var9 != null) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1xhssauzigh7x","pemAjkKOcI3b6RUfSDlh4xuu5LrOC4fTqCVQq/76K7E=",-8325039508375903063,-735666274217365432,2958582670094196458,-674519255097100876>()) {
                                 case 529211719:
                                    switch ((int)com.yiyiaddon.m.b.a<"smmdkuqn1o6s8","PF3gWOPmYlUvngIQy9y4fwXEz5flsU2et8SGve4f0uc=",7520050591889534180,-5293977241480042390,-4428263809875794776,366475566167462675>()) {
                                       case 1434437699:
                                          return var9;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              com.yiyiaddon.g.d.a var12 = a(
                                 var1,
                                 var11,
                                 (String)com.yiyiaddon.m.b.a<"s19s8tsdmdepq5","TP2RYuEdhxXtc3D4cOQ9GB6Ij7wckw/MwsYlkOYEjok=",4435241675159313472,1435075750801957811,3451839741843367220,-3865650764743286158>(),
                                 (String)com.yiyiaddon.m.b.a<"s37l6m2xz04sy9","ychzKVKE4NV35FbclZ6/W90f2qWsazo0OroI0SMFBWpwqEbmhfcf8wxE1nUSNW6r",4959662654013011255,-7217455975112799953,-211667336071582314,-1679005220677081449>()
                              );
                              switch ((int)com.yiyiaddon.m.b.a<"s15x3gxmjd3f2u","mill42xiIvXPWkQgpKdU/xBd2kFo7Cx2Alerb0GYVoQ=",3815248648201963491,-3308406412448390081,-198319693960366625,-4159563610601286755>()) {
                                 case -32817454:
                                    return var12;
                                 default:
                                    throw null;
                              }
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"snqkhyiypapa0","7MFmvDgGZ/+Ynko9bQb0QzGD+5Zb7Yod69am0vSwmnE=",-5521021156368812711,4226048531981005915,-6073042296307309158,3042695155773177653>()) {
                           case -486057529:
                              break label74;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               boolean var6 = var4.has(
                  (String)com.yiyiaddon.m.b.a<"s27ih9goddtlt","ENRC2WAsDCKT90NYX7v/8Nl4oqw8Lw1eV1qTysDf/NmFFmAZM2NBTOLw9lBq1A==",-1484197651706183383,6330737924596733959,7925060598217396146,4884615475909087223>()
               );
               String var10000;
               if (var6) {
                  label65:
                  switch ((int)com.yiyiaddon.m.b.a<"s2nqnqwc0lxgi4","hIAtcOFql0L4d6IoyuADObqmBvU0D8ZsM0yvBT6KJxY=",2533793735685561521,1905379770153751233,-735402070690921342,-5765565792686198747>()) {
                     case -1006319594:
                        var10000 = (String)com.yiyiaddon.m.b.a<"s2oiy3plnuyjnq","k7bWjJXnzmDTIxRv+yQQ4vVW9C2swjf3qJwKni4up4tl95y7Z2kfbA1NkNZQeAjhiH99jwCi5e6WMhZmWdjLFccQAuqtkhfXTcdqgTc1MxF8oV/7vhMrFJSc",-5277077624373425204,6569350207691412652,451974167429617890,-4787726560250593825>();
                        switch ((int)com.yiyiaddon.m.b.a<"siaeca06q521o","b5Eo4kDD4R4No3bWWQGxzcCYuVTcLqZkCzi1DMqEFGc=",7715566120046684746,-6152647587970412102,8749487076223942310,-9183844195528124823>()) {
                           case 463238449:
                              break label65;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var10000 = (String)com.yiyiaddon.m.b.a<"s2520wrvr6jy2u","3gNugc42LFBmgJfQ4EKc4lHhLbOhn5wtOuywSyvBvnkBu2KEwxULIJX9kaPPHW8EMSowgvC4wtx60Jpe",-3307692515474276644,3575011769189900402,2091587863652651343,7047115958649938734>();
                  switch ((int)com.yiyiaddon.m.b.a<"s3bj7puwcwooms","Qvr6rcCMPh8i32HiMWvTAst9k1bO9pqaeh2fFpiQEFY=",-8149314858943225654,-5258308761952262700,-4504100230366142355,3318745538634736301>()) {
                     case 419560936:
                        break;
                     default:
                        throw null;
                  }
               }

               String var7 = var10000;
               return a(
                  var1,
                  null,
                  (String)com.yiyiaddon.m.b.a<"s19s8tsdmdepq5","TP2RYuEdhxXtc3D4cOQ9GB6Ij7wckw/MwsYlkOYEjok=",4435241675159313472,1435075750801957811,3451839741843367220,-3865650764743286158>(),
                  var7
               );
            }
         }
      }
   }

   private static List<String> a(BlockState var0, JsonObject var1) {
      ArrayList var2 = new ArrayList();
      StateDefinition var3 = var0.getBlock().getStateDefinition();
      Iterator var4 = var1.entrySet().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"sf0nshpfegk4h","hcJsunk6DPXM7jEk9pG3v6V88CclXR7UrhLuSxZN5O4=",7996183555829451782,-8123143721113040171,-4482952693746410289,-2391564872516889871>()) {
         case -2127069781:
            while (var4.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s36u0eqyspdxnd","eKK1yc1P29r4OGZhUB8ZwCvKg8Op2lW04N1yRAPnRv0=",3586488382064346120,-4679574609388384163,2621499017307628993,125657239733346141>()) {
                  case 41923546:
                     Entry var5 = (Entry)var4.next();
                     if (!a(var0, var3, (String)var5.getKey())) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2dv5277shba2c","Z89S4Wc2zx2kcpiaAKaGPH/9rt2tANxs1gAH/ZsnEa8=",5024585469777345921,1855898628931869874,-1549565632991076148,-1115196826458812896>()) {
                           case 1861824920:
                              switch ((int)com.yiyiaddon.m.b.a<"s1p0ucupcp8wh5","wpjA3Iwecw27U4UVXw3PvNIY1UDkw4Jo2t8cXeSJ5UE=",3965991545739651167,1984896233351339814,-1182244107951401984,-827981190685105285>()) {
                                 case -1413433901:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        a((JsonElement)var5.getValue(), var2);
                        d.bi((String)var5.getKey() + "");
                        switch ((int)com.yiyiaddon.m.b.a<"sp9jq20vc6baz","V41bMAxRRHeRN9QDoheaScQlsp888Cu4Zhey6vuAqWs=",-766643764700329926,-2180042632203332287,573743896610850572,-8029045723812425423>()) {
                           case -1148277141:
                              continue;
                           default:
                              throw null;
                        }
                     }
                  default:
                     throw null;
               }
            }

            return var2;
         default:
            throw null;
      }
   }

   private static boolean a(BlockState var0, StateDefinition<Block, BlockState> var1, String var2) {
      String[] var3 = var2.split(
         (String)com.yiyiaddon.m.b.a<"s2lz3z5apq56p1","O9Gna3xQenvByx//bg+8dFAqTH+EACQAysCfVOc1",7542072678551241508,2039717482416932682,4343928245405667629,3482678636260855895>()
      );
      int var4 = var3.length;
      int var5 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s3ol4o7on9k9ld","48DOtW02CP1gFQrIm3t/lgfkwamYy1N3qVMVJGrqxlE=",-348553058583711748,3134226481691058770,7954266023614679848,-7605597652649609468>()) {
         case 198012269:
            while (var5 < var4) {
               switch ((int)com.yiyiaddon.m.b.a<"s1owyo7con27ro","AQV+9QJITO/8xun0j+32+aKtMAxQbdjqFUkES06MV6o=",-7208050931633549174,3054377485788513510,5079442645294804308,6066208288947061000>()) {
                  case 496363754:
                     String var6 = var3[var5];
                     int var7 = var6.indexOf(61);
                     if (var7 <= 0) {
                        label43:
                        switch ((int)com.yiyiaddon.m.b.a<"s10hve5kf8kdfs","mrHN9qaLrN/8VpWNrVTDjjL8yCiNhvA5fkDVQXXOgKY=",1604910221455250608,6630992290668364191,-1255796984294066945,-2375131705610134539>()) {
                           case -1627527812:
                              switch ((int)com.yiyiaddon.m.b.a<"s2iozasm2rmsw8","bsuqT7Obin4f6GqzjMSInnxHBz5eQvyH1dOPMOaCs50=",3054783912615437251,5417419119478274671,-1474701925973342210,-2874434355443763017>()) {
                                 case -1458937802:
                                    break label43;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        String var8 = var6.substring(0, var7).trim();
                        String var9 = var6.substring(var7 + 1).trim();
                        Property var10 = var1.getProperty(var8);
                        if (var10 == null) {
                           switch ((int)com.yiyiaddon.m.b.a<"stkl034lnudx8","ovC+PBTBJcjVRIUWEF1/bw4q6hQoDw46YvHrnm2fQtk=",-68354495636046928,3143284160355062617,1814626623146794721,-6606439757303338437>()) {
                              case -1144685272:
                                 return false;
                              default:
                                 throw null;
                           }
                        }

                        Optional var11 = var10.getValue(var9);
                        if (var11.isEmpty()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1lzjo4yx1xyph","9anbG7PnXzezpzYTg/ryqBVNi/jHWoRuLmTCnrC5ius=",-3879803419005204703,-6977258884314808753,3071256221578014065,1897410897981187327>()) {
                              case -60234604:
                                 return false;
                              default:
                                 throw null;
                           }
                        }

                        Comparable var12 = var0.getValue(var10);
                        if (!var11.get().equals(var12)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s24wz7edwq2jpx","lgnlnVU9eH4hBCJA0/F/gkSESOwHDZOFxG292PGb8Ro=",-3031651019418813270,-1103313858171844606,533925398325135740,-6768987641127869378>()) {
                              case -1956873443:
                                 return false;
                              default:
                                 throw null;
                           }
                        }
                     }

                     var5++;
                     switch ((int)com.yiyiaddon.m.b.a<"s22wpthmviym7g","c0dtJIN6GdXmfy3oqGqMKrPPtOZQZ4yPwEQn2tbe1Sk=",-4943625575583818465,-5851909708138789649,-132939321980161885,-6207838233249064619>()) {
                        case -2070054515:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return true;
         default:
            throw null;
      }
   }

   private static void a(JsonElement var0, List<String> var1) {
      if (var0.isJsonObject()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3bkwxtconkrwu","gtlnAQagJ3Kn+WDW/LqJ4Pld3k68R7nkWl04HosmrTs=",7824375526203743238,5287063453920917840,1155567451305490448,-4643064172988853866>()) {
            case -1790220675:
               a(var0.getAsJsonObject(), var1);
               switch ((int)com.yiyiaddon.m.b.a<"suw5j7nuvk0qi","NrwN3DQntgSVNMAjqDrYcNu7l9WofJXf07qKv+xOUxQ=",-1596898851701382316,-9070621820320194398,-3778726200836091863,6106773356067561358>()) {
                  case -1772703960:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else if (var0.isJsonArray()) {
         label28:
         switch ((int)com.yiyiaddon.m.b.a<"s2nnii6txlsi09","inxtg9uAuHToknx9dqxYh1FZpboLSs6qNbJBFrvo2Zk=",5857978720239315394,8249004929559894100,-2591596566185950363,-2447847242528154214>()) {
            case -382789942:
               Iterator var2 = var0.getAsJsonArray().iterator();
               switch ((int)com.yiyiaddon.m.b.a<"s2gimypffl0q8j","qWE8G+DPhGPka3xodIAh3Qa6gmd1Ba+VDU18y3EpnK4=",422412313866435456,2713962243893474766,-4785750842676374663,225112561719537855>()) {
                  case -1259415618:
                     while (var2.hasNext()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3o06f36bk6nha","KruyipBLcQX6j6B1CYk72fM8GiU/5tKkdUWlRQqP9KM=",-3295996324768437240,4325419232152088160,-56358919409316533,1228447341144637791>()) {
                           case 420760406:
                              JsonElement var3 = (JsonElement)var2.next();
                              if (var3.isJsonObject()) {
                                 label33:
                                 switch ((int)com.yiyiaddon.m.b.a<"sffo9nmzonaau","4Ai8KoJHW/0ZSSsJnPm5IVIVtnCyCx5qPQtBZ5MVm6c=",8705479559178094394,-6495251419466131351,-4837744867203707176,-6825729873584820983>()) {
                                    case 2115163197:
                                       a(var3.getAsJsonObject(), var1);
                                       switch ((int)com.yiyiaddon.m.b.a<"sq9c429f7m5ib","fqRLbyw0BHjyjCkeP1Sp2xFGg1zG8/Tmfcox1TVAujA=",-2693463703777262530,3707790951570304121,35420666523705122,4755815776151017353>()) {
                                          case 1905634549:
                                             break label33;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s10z4pfkla5xqd","1oE5qMUbLHLOfACL3PUOxDxi7Qi4d6ETx3zJr+9wfmk=",5270458484441937279,501344788699497594,307654614015236157,4455745297502274542>()) {
                                 case -657732486:
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
   }

   private static void a(JsonObject var0, List<String> var1) {
      JsonElement var2 = var0.get(
         (String)com.yiyiaddon.m.b.a<"s39rygdi3qv1yc","ppkcFO5VJ6cQ2acLCeOqD2mZGogpSalo3Q8LgTkBurVknwGXi9E=",1683313664231662675,3738336729778095354,-2823519145577464368,-8551798428005171096>()
      );
      if (var2 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2a5nabuotnbw4","0z45talrkqWvsSG+1cuKMS8+pmFK3S8tA6ZC+tPFBWE=",1668769908499846474,9064218514486596024,-3761500680019568466,-938691108626117799>()) {
            case 315713975:
               if (var2.isJsonPrimitive()) {
                  switch ((int)com.yiyiaddon.m.b.a<"sprjdhusnwzld","BFtlMfFG8PZ0fDE4P9IIFt5wQH57Gpc8gJU/e3is4MY=",-1014578951352437804,711933093704960292,8320355818033472969,5474411485945995554>()) {
                     case 1809412167:
                        var1.add(var2.getAsString());
                        switch ((int)com.yiyiaddon.m.b.a<"s31345pf8l1ug5","cSkH30THtZFjWDZd7AlIHVe2T+KOJ9u8abmWVRAOq/o=",3611916416223785083,-5043176572909501719,-6143680885228014276,-233759857923937168>()) {
                           case -1564827595:
                              return;
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
   }

   private static com.yiyiaddon.g.d.a a(String var0, Identifier var1) {
      Identifier var2 = Identifier.tryParse(var0);
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s342bt6dsoj5u3","YZEo98M72etGvjHohJEIwswuChXAebO7LaRtATolhFk=",-4515982832974718617,-4522012267612682274,-7103559426032296822,2413453196515467235>()) {
            case -1925453213:
               return null;
            default:
               throw null;
         }
      } else {
         String var3 = var2.getPath();
         if (!var3.equals(
            (String)com.yiyiaddon.m.b.a<"s2zecsgymwhbsa","iD4V+ct/JZcN9U4RG1sSR6bS8HWcvYesas/IGGrtGVq69hnXhwc=",1035757311424643246,-5617385837815209754,-7308716736617712801,-4998013152710979197>()
         )) {
            switch ((int)com.yiyiaddon.m.b.a<"s2k0iwv308aezd","tCfYMpn8pj/E+zhrfJWW6BrZ0yl3CYbJ6DRcBFvshy8=",336558831678942498,3759163118401277100,-4496692740505386141,4747929252794110753>()) {
               case 1023355822:
                  if (!var3.equals(
                     (String)com.yiyiaddon.m.b.a<"s2wfpo83revzsr","ktPdFir7pgko4iD1VKWllX5Yju4aEAxhr4Z5mBYizJYtA0fSTWWvQIHg1ge+1W9jkLs=",-8949233763451849556,3776734276071634723,6225618621305213783,2891414540756628172>()
                  )) {
                     String var4 = bS(var3);
                     if (var4 != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1dbcngalcfgil","NBgd1on7/cp0uEKmukNYoB8brJmfKlMcjLnQJcUW2Pg=",-7152588451161173381,9094299762136734269,6953755045967399154,-2572133260868653174>()) {
                           case 838266368:
                              if (!var4.isBlank()) {
                                 String var5 = var2.getNamespace() + var4;
                                 String var6 = u(var2.getNamespace() + var4, var4);
                                 return new com.yiyiaddon.g.d.a(
                                    var0,
                                    var5,
                                    var6,
                                    (String)com.yiyiaddon.m.b.a<"sdnxtnr4em93c","dgH6xi6h7NYqQwSo7BDMfcofAaBa0TWPT2dZHFSgxYs5+Cr4z78=",-7470036329857968099,-3301356464402912291,3843615311164843931,-3568060009074446090>(),
                                    (String)com.yiyiaddon.m.b.a<"sr1w0czf9ux5z","bufu49xAJGqJEU+jSteDU3S5M8mB9QCUNK56OmrVZsO7lw==",8831071228280630002,-4339602128922636460,-7430673883203395742,-6728967049305382243>(),
                                    null
                                 );
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"sfoupdd46v45","//+gdo9yCWIICQw2Cheo/tyDGKEn9P3a7Tmw7ksUjQk=",5717097423022789868,757781311036133903,949930840146138895,3124856204188972658>()) {
                                 case -370431183:
                                    return null;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     return null;
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s19ffs1ecxwkf9","OvBXWRh3WWAYSFCL+dRusRWm4BfVO2tXZoHWBovk+xA=",-3922183451087439625,-510920521668772624,-195988848551573685,-1169457113563606010>()) {
                        case 1628860189:
                           return a(
                              var1,
                              var0,
                              (String)com.yiyiaddon.m.b.a<"s19s8tsdmdepq5","TP2RYuEdhxXtc3D4cOQ9GB6Ij7wckw/MwsYlkOYEjok=",4435241675159313472,1435075750801957811,3451839741843367220,-3865650764743286158>(),
                              (String)com.yiyiaddon.m.b.a<"sc7l7gtb4xzpt","nu7qQJDJ9ONLtkS7Xk0HPBT61AQQ3hpD488xb2fTQt5iiWndF6TJJK2X8C2qggryLnsgnuAjjsVOz1ZeCeN+qMbImuBUH+m+qr1BI+JBHJoIqwy2",6865683095482532391,2178064814503877983,4536178962981802648,4363242010494985030>()
                           );
                        default:
                           throw null;
                     }
                  }
               default:
                  throw null;
            }
         } else {
            return a(
               var1,
               var0,
               (String)com.yiyiaddon.m.b.a<"s19s8tsdmdepq5","TP2RYuEdhxXtc3D4cOQ9GB6Ij7wckw/MwsYlkOYEjok=",4435241675159313472,1435075750801957811,3451839741843367220,-3865650764743286158>(),
               (String)com.yiyiaddon.m.b.a<"sc7l7gtb4xzpt","nu7qQJDJ9ONLtkS7Xk0HPBT61AQQ3hpD488xb2fTQt5iiWndF6TJJK2X8C2qggryLnsgnuAjjsVOz1ZeCeN+qMbImuBUH+m+qr1BI+JBHJoIqwy2",6865683095482532391,2178064814503877983,4536178962981802648,4363242010494985030>()
            );
         }
      }
   }

   public static String bS(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2depiwceqcoo2","cfcF5gSaVE0V6Ga8qFW0p1n4/hEZMUWaajagyXI+iGg=",-5950340196099040073,7478565066319770928,-4061146221596857394,7334797322985974007>()) {
            case 1892410470:
               if (!var0.isBlank()) {
                  String var1 = var0;
                  int var2 = var1.indexOf(58);
                  if (var2 >= 0) {
                     label116:
                     switch ((int)com.yiyiaddon.m.b.a<"s29azcuf12ecvj","6cdPLcXu/m40lHDEbxzcqUR+Xdb3BmKHtHZ5N7E9SNM=",-4367711943702994946,-1567708243647676039,5391566810674192701,844126992573800590>()) {
                        case -825984736:
                           var1 = var1.substring(var2 + 1);
                           switch ((int)com.yiyiaddon.m.b.a<"s3al7kg1my3b5v","0HielEU3Dhv217tcqffE5SCVCqKMSFNE8efENem3fR4=",7435946407594603780,685381484091877608,-1259756420366773798,7834964716935779686>()) {
                              case -1102651053:
                                 break label116;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  if (var1.startsWith(
                     (String)com.yiyiaddon.m.b.a<"s4o2k2i0mg96y","zgte514vql0vMff9+Itg+UiQ2yQjGYJnf6QfSvgV40FEvbAmhxRH8g==",6032257049122222705,-5401025605977258524,-4915755761581042412,6929905876346650196>()
                  )) {
                     label111:
                     switch ((int)com.yiyiaddon.m.b.a<"s348nttpylxrds","0YGmbG86vrJ9koJlT3oIHbr9ktM6Ddbf4deyWjmuI1Q=",8908618936007251847,9119633469537957216,-5861481642278538805,-1657301666254509577>()) {
                        case 921752866:
                           var1 = var1.substring(
                              (String)com.yiyiaddon.m.b.a<"s4o2k2i0mg96y","zgte514vql0vMff9+Itg+UiQ2yQjGYJnf6QfSvgV40FEvbAmhxRH8g==",6032257049122222705,-5401025605977258524,-4915755761581042412,6929905876346650196>()
                                 .length()
                           );
                           switch ((int)com.yiyiaddon.m.b.a<"s1wttulc6dej1","4J599eIyuwgml39PrPuIhx+w5tkOWj7d6oWEDQcIARU=",-669975289050270877,6187294867088787824,-3411950380733364797,-4354537109569568775>()) {
                              case 606619707:
                                 break label111;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else if (var1.startsWith(
                     (String)com.yiyiaddon.m.b.a<"s1km2ic6vx036x","1ozVxab4sXCFEBcxHu2qqvy2+PcoaLSUz9s3+fTj/HOqGYWX4no=",3696209043181752149,4666976814599778457,7653561451423296090,6300709292130146456>()
                  )) {
                     label107:
                     switch ((int)com.yiyiaddon.m.b.a<"s2npver8vt8tc6","C3H5ycZt2suBPEGjewab6nQgvWehnzas8oJQcyZMz6o=",2348284385808494832,3892280826537973368,-7892333273502218881,2543577669778161258>()) {
                        case -1645099514:
                           var1 = var1.substring(
                              (String)com.yiyiaddon.m.b.a<"s1km2ic6vx036x","1ozVxab4sXCFEBcxHu2qqvy2+PcoaLSUz9s3+fTj/HOqGYWX4no=",3696209043181752149,4666976814599778457,7653561451423296090,6300709292130146456>()
                                 .length()
                           );
                           switch ((int)com.yiyiaddon.m.b.a<"saz1vpr74a3zw","oj6cLuOmUy6BcISRMmjvIEr9Q9HCe1wINWiT2fVgY0g=",-1557936551410124461,5264035132192142555,6372373137461988841,-5308537296569707809>()) {
                              case 796000224:
                                 break label107;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  if (var1.isBlank()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3q6stfv4havye","w0uOuvDdBk/Z3Q0GLOL4cP2JB5VSdEYC/RlIt6a6SY4=",-1173180975160268140,5747554277155203075,-614002230140016574,-8693221506096912118>()) {
                        case -356959616:
                           return null;
                        default:
                           throw null;
                     }
                  } else {
                     int var3 = var1.lastIndexOf(47);
                     String var10000;
                     if (var3 >= 0) {
                        label98:
                        switch ((int)com.yiyiaddon.m.b.a<"susm9hu7mshrz","SuRkF6EzHyf87lhS6x9rNRzy6t7A4ERf/EDm8ZypA4U=",222384284351508832,3581316473776783408,1843291297032865212,-4760515755051497613>()) {
                           case -1690758665:
                              var10000 = var1.substring(var3 + 1);
                              switch ((int)com.yiyiaddon.m.b.a<"s3px79sqyhu5kl","B7m4LZZeVPvmOBB8LHe1BgBkCvT5gpkW7uCNE0CyOGA=",-849146619990144221,5306548034610969795,-7516986313963800188,-9192466522008486531>()) {
                                 case -936280706:
                                    break label98;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        var10000 = var1;
                        switch ((int)com.yiyiaddon.m.b.a<"s3dbpm52uh0fap","TNdUZ1wniiTmONxtjlMjzzaAM9LS83FhhHvjExODhLE=",2813469423177489839,1155367016198211073,-6315649363133435679,7900639319257609907>()) {
                           case -538156712:
                              break;
                           default:
                              throw null;
                        }
                     }

                     String var4 = var10000;
                     if (var4.isBlank()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2kn2lmljdejsf","NNxwh6aoEmD7oUKIV8mtqkbAooNdGUX0li6RDEVhViI=",-3964542260289171691,6569122593852892783,2003289398180301860,8879933616403131639>()) {
                           case -486244722:
                              return null;
                           default:
                              throw null;
                        }
                     } else if (var4.startsWith(
                        (String)com.yiyiaddon.m.b.a<"scvrsemgujcy3","LtUiEHOmMD8JDLuYANzdBFX3kCmq3fxwNzRwTw7tkKFOcUy61Gc=",7143497307376203921,90835202181112787,6131091003812236911,6711065469677438783>()
                     )) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1ka7wj69r2rmo","G2qQXKx3eiPvWtj9i8sh//jk54nvYElh3pkUcEONrQo=",-5198954653205002944,-6727985989719139130,9144613158017599084,-6838697233609112779>()) {
                           case 2142018047:
                              if (var3 < 0) {
                                 switch ((int)com.yiyiaddon.m.b.a<"syohjua1w3kgt","SYPv1HWyGPYnNP/mVlz8f0j6EBzr2BhMl34IgkXMHMA=",-2425386882315784553,-6921936605906494300,-1259946532942894216,-4828920182438332809>()) {
                                    case -1694534392:
                                       return null;
                                    default:
                                       throw null;
                                 }
                              } else {
                                 int var7 = var1.lastIndexOf(47, var3 - 1);
                                 String var6 = var1.substring(var7 + 1, var3);
                                 if (var6.isBlank()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1fer2bx98eozw","aKZz+ENa+fGvwBBighhxPOGR2qfeVABhOFtj+PUPpng=",-1340181469174649278,2865523359542892034,6528848269936120339,7006499155802087745>()) {
                                       case 1825086636:
                                          switch ((int)com.yiyiaddon.m.b.a<"s3qo2rezx3ang0","E7qjSoeYt1iG62W+ghQrDuLAQKte/ayqwJTvYlmQcz4=",-2089714255660632916,-5169667723283856690,-8129944333287522479,-6924790839253264748>()) {
                                             case -1993245535:
                                                return var4;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    var10000 = var6 + var4;
                                    switch ((int)com.yiyiaddon.m.b.a<"s24xlhgurfdbls","0GmAEgX5AWLkEnT5u/sWw7LtuNWTvhQrqTF1VgM+408=",-1947518461359238443,-8071016894081693749,-4393072661458820857,7868780715755533047>()) {
                                       case -346447278:
                                          return var10000;
                                       default:
                                          throw null;
                                    }
                                 }
                              }
                           default:
                              throw null;
                        }
                     } else if (var4.contains(
                        (String)com.yiyiaddon.m.b.a<"s130ylja2ny7jl","vbAnJx4PXuPnqF+5vE2GW97dnfAJXZ3BOhGt43ulXGZLWeA62JrfM2jp",-3475221925825261361,-8454501432025245627,8922007757743639094,6394208526124775469>()
                     )) {
                        switch ((int)com.yiyiaddon.m.b.a<"sgpw3gtv8qay2","yv+VC0dSoz4uI6bu77/FdVnS03VJhKJc4VUSsH6OIkc=",-7674111668440947461,-4192677664896505126,-1700669018854087616,7325897015806622476>()) {
                           case -645069151:
                              return var4;
                           default:
                              throw null;
                        }
                     } else {
                        int var5 = var1.indexOf(47);
                        if (var5 >= 0) {
                           switch ((int)com.yiyiaddon.m.b.a<"scek448t5e9n4","xBYMU5kyfEqK6vaGHLGy4wptmEA+CzIFKy8uM6D002g=",5708612861734964296,3072960317620740955,-7913055173777980578,8408751682411907959>()) {
                              case -1870605303:
                                 var10000 = var1.substring(var5 + 1).replace('/', '_');
                                 switch ((int)com.yiyiaddon.m.b.a<"suo6o85365qro","y083RnhozQIpfP5uJXd/PmprYnkpMboUY9wILgMcsfM=",-2707091781386814608,-3037159723600369685,-8054944548534920022,-3282298440314388875>()) {
                                    case 1604827217:
                                       return var10000;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           switch ((int)com.yiyiaddon.m.b.a<"sx9db1uiqc2ia","G7C+fOIbO1zzr64ojDlY6Q5+wHzLrtKQxH+2CdEksQA=",-7328972093335919575,-5663886150359661727,6427377225371889680,4667839920953445059>()) {
                              case 476378063:
                                 return var1;
                              default:
                                 throw null;
                           }
                        }
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2zxto7z7qjqyt","lsIPVpFz/2bo19FT9TbO01BUkh73gv5bWJngoySkHfY=",-6885177736834974644,4619924851143569126,-1958003015180431134,-3711348958518614671>()) {
                     case -1684718847:
                        return null;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return null;
      }
   }

   private static com.yiyiaddon.g.d.a a(Identifier var0, String var1, String var2, String var3) {
      if (!(String)com.yiyiaddon.m.b.a<"s1ov901jlti2wu","5u/N3W7slKZdfc8Z6QbjvA53paOlNYJzNMsXtYfN3kD1w4Xz01LciW7FYFV8Rw==",7007030035382409565,-7038335697274417246,-3515835399028531624,-2490063434331567752>()
         .equals(var0.getNamespace())) {
         String var4 = var0.toString();
         String var5 = u(var0.getNamespace() + var0.getPath(), var0.getNamespace() + var0.getPath());
         return new com.yiyiaddon.g.d.a(
            var1,
            var4,
            var5,
            (String)com.yiyiaddon.m.b.a<"sdnxtnr4em93c","dgH6xi6h7NYqQwSo7BDMfcofAaBa0TWPT2dZHFSgxYs5+Cr4z78=",-7470036329857968099,-3301356464402912291,3843615311164843931,-3568060009074446090>(),
            var5 != null
               ? (String)com.yiyiaddon.m.b.a<"sr1w0czf9ux5z","bufu49xAJGqJEU+jSteDU3S5M8mB9QCUNK56OmrVZsO7lw==",8831071228280630002,-4339602128922636460,-7430673883203395742,-6728967049305382243>()
               : (String)com.yiyiaddon.m.b.a<"s19s8tsdmdepq5","TP2RYuEdhxXtc3D4cOQ9GB6Ij7wckw/MwsYlkOYEjok=",4435241675159313472,1435075750801957811,3451839741843367220,-3865650764743286158>(),
            var5 != null ? null : var3
         );
      } else {
         return new com.yiyiaddon.g.d.a(
            var1,
            null,
            null,
            (String)com.yiyiaddon.m.b.a<"sdnxtnr4em93c","dgH6xi6h7NYqQwSo7BDMfcofAaBa0TWPT2dZHFSgxYs5+Cr4z78=",-7470036329857968099,-3301356464402912291,3843615311164843931,-3568060009074446090>(),
            var2,
            var3
         );
      }
   }

   private static String u(String var0, String var1) {
      Language var2 = Language.getInstance();
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"suispco1gfxrd","2Q6h3uCUQfDFqpf9PxYIoxMTtA8nmfj3eMKFmfZPRxU=",6183906108864290114,-5465297256637012558,-6813979309614675242,4719643621684756914>()) {
            case 2125707196:
               return null;
            default:
               throw null;
         }
      } else if (var2.has(var0)) {
         switch ((int)com.yiyiaddon.m.b.a<"s1p4rcz4ayzuzz","v8a3Xj7ThNMyRxkiU3JmscVREILbEOMdS7h7vBz6UxI=",-2894950428155644862,2496597389149874885,4762920373216900338,-555062970640611666>()) {
            case -849400480:
               return aj(var2.getOrDefault(var0));
            default:
               throw null;
         }
      } else {
         String var3 = var1 + "";
         if (var2.has(var3)) {
            switch ((int)com.yiyiaddon.m.b.a<"s3klvnpvp98uyq","9aX/HhuScaVdfysRYSgFkPtl29yrekwz4Cn8Mak/GAs=",4931222254218577713,-6083881164695658674,-8196494563959139034,-6742570974808686121>()) {
               case 628953173:
                  return aj(var2.getOrDefault(var3));
               default:
                  throw null;
            }
         } else {
            return null;
         }
      }
   }

   private static String aj(String var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3o5c2i86hv7p1","ZeKJVluCVu0D9mNwnUmcqfjLFfIyUsdgbK70kdCydww=",7174956575689910019,-9207731071641860710,3298888210104030237,-2260005749371434857>()) {
            case -1097041507:
               return (String)com.yiyiaddon.m.b.a<"s2vp0id3lc2paa","a5H6bH6JaadFQHQ8O2j8+rrcb4J8FajShX8KGg==",-7859461375153699294,-9048529271138135381,-6356038514552481362,-5231770825461238090>();
            default:
               throw null;
         }
      } else {
         return var0.replaceAll(
               (String)com.yiyiaddon.m.b.a<"s1kh2xtmvbzbd4","UzQSMo+YBOAtzFKhUgJwvNDPo+my/foEf5bd1sav4SpdiTPelHIlXNR+1TEmKO5SL4iDzMdqCsuczE/hBQIld8EesDQTmA==",-5098796289316113023,4495281093121439031,-5867809015422239761,7350351018367390195>(),
               (String)com.yiyiaddon.m.b.a<"s2vp0id3lc2paa","a5H6bH6JaadFQHQ8O2j8+rrcb4J8FajShX8KGg==",-7859461375153699294,-9048529271138135381,-6356038514552481362,-5231770825461238090>()
            )
            .trim();
      }
   }
}
