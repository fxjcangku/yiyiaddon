package com.yiyiaddon.f.a;

import com.yiyiaddon.l.b.i;
import com.yiyiaddon.l.b.n;
import com.yiyiaddon.l.b.t;
import com.yiyiaddon.l.b.w;
import com.yiyiaddon.l.j.m;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;

public final class a extends com.yiyiaddon.l.h.f {
   private static final String BJ = (String)com.yiyiaddon.m.b.a<"s2gb23l230nf4x","hGqXHOLI5fVOXTgI04aPdv9Av6X0Lo9TD5/SPXUg",-3550137107353070207,-6541407467166498077,-4798043380184027935,7269088242739904173>();
   private static final float eD = 24.0F;
   private static final int rX = 64;
   private static final int rY = 3458905;
   private final String BK;
   private final Map<Block, List<Block>> aJ = new LinkedHashMap<>();
   private final Consumer<Map<Block, List<Block>>> j;
   private Block i;
   private String vg = (String)com.yiyiaddon.m.b.a<"s31wcc02wdfhx6","AY0TLLOw4Xaihq6UoqX6vpJok03wi8xnZ1zimQ==",-1196055475851078419,8555190255124303770,-108331198122800907,1598930206529945408>();
   private final m a = new m(() -> this.vg, this::bd, 64);

   public a(Screen var1, String var2, Supplier<Map<Block, List<Block>>> var3, Consumer<Map<Block, List<Block>>> var4) {
      super(var2, var1);
      this.BK = var2;
      this.j = var4;
      Map var5 = (Map)var3.get();
      if (var5 != null) {
         var5.forEach(
            (var1x, var2x) -> {
               Map var10000 = this.aJ;
               ArrayList var10002;
               if (var2x == null) {
                  label15:
                  switch ((int)com.yiyiaddon.m.b.a<"s3gt9ssvvzeecv","iIJm5TeLbtCvtAhzy7P4T5/29n35XrsYm/5QixH5RBc=",9193510357689888944,6902598336377505799,5142045472679938320,5968475212239320535>()) {
                     case 796584516:
                        var10002 = new ArrayList();
                        switch ((int)com.yiyiaddon.m.b.a<"s3n9lo7zd0483m","69wA4AqXoJu2maadWRcJisKBCAo8ZP/uR/eiJf6RU4c=",-4796199581446853231,3733367370696499861,7244545096007299013,-5352447144748609119>()) {
                           case 336186213:
                              break label15;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var10002 = new ArrayList(var2x);
                  switch ((int)com.yiyiaddon.m.b.a<"s2kqezkog23avz","v4WaFkaa+W7I6sx6ff4ngMmR1EWshrB5bVxz323PRMw=",-448881423758417038,5592432107733464514,-5286660131390294661,-6694255031613283885>()) {
                     case 399325332:
                        break;
                     default:
                        throw null;
                  }
               }

               var10000.put(var1x, var10002);
            }
         );
      }

      this.i = this.aJ.keySet().stream().findFirst().orElse(null);
      this.u();
   }

   private void u() {
      i var1 = this.d();
      var1.b();
      var1.a(
         new t(
               (String)com.yiyiaddon.m.b.a<"ssaasl86tedh5","RrBgdzJPBVLkTj4Jm8eUtkKsR9gToMba9B0ypj77YhE=",5402839397736226744,3353812172458166067,4297813172638930100,6848192883988183859>(),
               this.a
            )
            .a(
               () -> (String)com.yiyiaddon.m.b.a<"s36tgnrtzddgz2","RgGsnhIFoYOKix/R2yYhkTuDMyy2tu1/02mkcgZ4Eb2x+kmacjv6RvxnJHizrBDw639hRhD30Ez/vA==",4510249165479534033,3138623751429638017,-7472829727809230511,8554764565044083626>()
            )
      );
      this.O(var1);
      this.P(var1);
      var1.a(
         new com.yiyiaddon.l.b.b(
            new com.yiyiaddon.l.j.a(
               (String)com.yiyiaddon.m.b.a<"s34zmtzh53fyyi","0pXCk23RhV8zcgFKXMdfFceQ61inJQzCNAZafMBWWc7o/lSH7V81nai+",2154328893223117525,-3965402513520908485,-7245505403776473463,2145840969282303088>(),
               this::je
            )
         )
      );
      var1.a(
         new com.yiyiaddon.l.b.b(
            new com.yiyiaddon.l.j.a(
               this.i == null
                  ? (String)com.yiyiaddon.m.b.a<"s20rm5zzmt28g1","R+yfnwMODLHKpMHUOVnxnxghaREjwxKf4NkS/BiaHS5V65iy0ld2mZxu2QepRTJ2GuohmTwQ4CZYrPvf",-8984561801832300487,863921756090914482,-7205776049756579989,-5956502304341529182>()
                  : this.i.getName().getString() + "",
               this::jf
            )
         )
      );
   }

   private void O(i var1) {
      var1.a(new w(() -> this.aJ.size() + "").a(24.0F).b(12.0F).a(true));
      if (this.aJ.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s39zb4t34twb8","TVPpQ/psP14SoYqYN1ZOtUA7AG8Q21ZcK/juHQVUyZI=",6398224831973125348,-4989921050940100860,-954329403978206919,-4061866765083055239>()) {
            case 2010950905:
               var1.a(
                  new w(
                     (String)com.yiyiaddon.m.b.a<"s2uo34g5zq9rxi","QHJG+2Hk6TllA9abtv63uzywxRcIvogqIyHGgZhh2NL+kdhy5TxnrTk/nxMfmTLQlF0C8A==",7471219155830193453,-122464371222575604,8480037408145214339,2068058830802801143>()
                  )
               );
               return;
            default:
               throw null;
         }
      } else {
         Iterator var2 = this.aJ.keySet().iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s1rnjx45ps0rp4","ZuigC+R1dHZu4MLpAn7IMluB8cio1G//k9AuG8i6hVM=",-6822557527490252719,-5190882494526889513,802460767266890865,1812310501378581138>()) {
            case 481628869:
               while (var2.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s114pqwvvvum4z","1Og8pku2SeazMmQ+jGn9XNBCbcs+jvjnhYRS0ALe3Fg=",5395956720237210200,-8037794542909384057,7139316106505542390,7133932215479830490>()) {
                     case -766202259:
                        Block var3 = (Block)var2.next();
                        if (!this.h(var3)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3p6wh95cb1hye","NNLwfUfccSK7guioDc7bp1Hs7NHtI3olTIMixNLoeTI=",2792430386177995945,-2446090736758372451,-1175878142148624711,-8140384851162796666>()) {
                              case -2102769241:
                                 switch ((int)com.yiyiaddon.m.b.a<"s3tv9erbt9w8fi","2S2YYOJ3VJ3RgptWUmzdZllx4P8DoUNAOULBBxFAnlI=",6650363913708642232,1001850586623429432,-4712445733686396682,-1755492268449707242>()) {
                                    case -1563481678:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           com.yiyiaddon.l.j.b var4 = new com.yiyiaddon.l.j.b(
                              (String)com.yiyiaddon.m.b.a<"s2gb23l230nf4x","hGqXHOLI5fVOXTgI04aPdv9Av6X0Lo9TD5/SPXUg",-3550137107353070207,-6541407467166498077,-4798043380184027935,7269088242739904173>(),
                              () -> {
                                 this.aJ.remove(var3);
                                 if (var3 == this.i) {
                                    label13:
                                    switch ((int)com.yiyiaddon.m.b.a<"s2poe3labiq2zo","DpqHzsJ7tYkobEiRsRabYppNn5ScKcVqhODTyhsmS+A=",-7486937098962041165,5109701988767616127,7557931827535400376,4529134798914168716>()) {
                                       case -1631983031:
                                          this.i = this.aJ.keySet().stream().findFirst().orElse(null);
                                          switch ((int)com.yiyiaddon.m.b.a<"s213tiz5x7t05i","Kx/euQCe+OVJWeTInl5gtRwvHMzC2XyxpE7bcGD1HdU=",756826866551091672,8932437189865686535,3245237546959451808,6583072749747765767>()) {
                                             case -954276083:
                                                break label13;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 this.jg();
                                 this.u();
                              }
                           );
                           var4.a();
                           n var5 = this.a(var3).a(() -> {
                              this.i = var3;
                              this.u();
                           }).a(var4);
                           if (var3 == this.i) {
                              label33:
                              switch ((int)com.yiyiaddon.m.b.a<"s27jzt6sf8opcc","66VO2Y8iaLXV6wMBawgdRHRuvon6/nhKidcKTlXX9rE=",4515734731984552478,7074254883697311152,-4319122009578869865,-8295078703273930498>()) {
                                 case 331390814:
                                    var5.a(
                                       () -> (String)com.yiyiaddon.m.b.a<"s37xad2genq5bc","C1WGwkMD9NWuwXU7cxytAvNvNBmYQ+SNUqoApXiSJZo5IQ==",5559720945964536970,3186602998069222177,-11329925114107162,2002114875111916621>(),
                                       3458905
                                    );
                                    switch ((int)com.yiyiaddon.m.b.a<"smzdpbi00joe5","mY4zdX8DxsHDBrXolwZNAZVnNIk075upONcarooyZpY=",-3972956005479298725,-5394473382087219519,7563835512308177283,-455383648846115801>()) {
                                       case 1011612552:
                                          break label33;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           var1.a(var5);
                           switch ((int)com.yiyiaddon.m.b.a<"s2fcly0ckf63sd","W833/BY00C7YOd7r04HQuH3g2YxqRByBnHrZZlZ432M=",-8894326221510664956,-3989061938339239465,220158967017832530,7039263424668597226>()) {
                              case 579118034:
                                 continue;
                              default:
                                 throw null;
                           }
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

   private void P(i var1) {
      String var10000;
      if (this.i == null) {
         label70:
         switch ((int)com.yiyiaddon.m.b.a<"s3bns48kehtsmz","laCHdmXWRsyhtubpvaPyPhGB4kp7Kyvs+NsipVYCBfM=",5376864834897730702,3371467825858439030,5628677666302633382,-9007045473456501535>()) {
            case 1934679860:
               var10000 = (String)com.yiyiaddon.m.b.a<"syr2t4zljs2ir","0nDuCX3mtVLFCn+OBIr0xvUmL52sMIGXLz6cceuc88//LA==",6275620219805237682,2328382949355665249,6213846899537043385,4316721631282237178>();
               switch ((int)com.yiyiaddon.m.b.a<"s1s3vj7cp9q2mw","COLPpd8+ZGTs6mXsSPbnerGn8ByvyIX4RksJR518anY=",-3164328007863828627,-8608869129066767576,9169534473735214341,6707103109952778260>()) {
                  case 1833838552:
                     break label70;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = this.i.getName().getString();
         switch ((int)com.yiyiaddon.m.b.a<"sn97rkmylpang","6KLuarl2LV+7RLggf/RJ1bVQHuq4WyqsCCR23BYUQ9U=",-5653377345990123527,-9042503398566445514,8009814901618208210,-4532068372865593657>()) {
            case 605077355:
               break;
            default:
               throw null;
         }
      }

      String var2 = var10000;
      List var7;
      if (this.i == null) {
         label63:
         switch ((int)com.yiyiaddon.m.b.a<"s3rh4zvy9xvf1e","XqhBYeXMw3a5Zdzdprb/sBf0WhuqF+IeqO7xCk3iKQE=",8024132332098740597,1459034924393734275,5162507791115455868,3508245269367999717>()) {
            case -928317963:
               var7 = List.of();
               switch ((int)com.yiyiaddon.m.b.a<"s1qj8r1mclcsip","IqSNmjdSHaTHopQBcrGcMAxmTnuTZBmBm7R05jOipVc=",-6185899376907585898,7564625043487772985,-9172263193059128486,6071730790910627741>()) {
                  case -638814143:
                     break label63;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var7 = this.aJ.getOrDefault(this.i, List.of());
         switch ((int)com.yiyiaddon.m.b.a<"s1h4m4w4iy76wi","H/MK8PG1mQG6pGVLYJIhmvkss3T6Vv6FNzP2tKXBgSw=",7977424123209237091,-5246241167817775939,1082369679564884953,-5780262901912728965>()) {
            case 1019359278:
               break;
            default:
               throw null;
         }
      }

      List var3 = var7;
      var1.a(new w(() -> var2 + var3.size()).a(24.0F).b(12.0F).a(true));
      if (this.i == null) {
         switch ((int)com.yiyiaddon.m.b.a<"shvh7qjlfq6q5","J7gCdVL0b7s8mo9u+fDFIhGzRYf4VfZQ8zNfYlzSU0o=",9021535588584056241,4040666337140696894,-8299554441114006756,5167301028826750721>()) {
            case 2062697529:
               var1.a(
                  new w(
                     (String)com.yiyiaddon.m.b.a<"s2lcjlauau2h2u","pUgg8ifJo7w18NFmb6qdiNX+YkyiojODR2A7PFqLwe/26rW4YmNQYUQI8ZpSFarwrc6ycHzCGalHqhW5iXq5LPSI",-2294480286562072351,2782246914872039282,-2986572274464439413,-945407891367654508>()
                  )
               );
               return;
            default:
               throw null;
         }
      } else if (var3.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"swvyikzloj13q","Qd6g03pcs2t9PnZBIx/1XCP0D6y2eAn6UPDqefrhk6g=",-3051059862538982238,2554560470325383265,-6540286500020809825,3637564601621490033>()) {
            case 626843911:
               var1.a(
                  new w(
                     (String)com.yiyiaddon.m.b.a<"s3gnenahsuq9mv","Rx1t930VKEARGklscVMHJxlX4L3i40W+Q89zjUOGfeyVdxVD4APyIAIntzyponsv4t7nHjB4gQ0rvQ==",-3856992531895236502,-5746160224283535290,-7004732637014885125,-7668216753883997120>()
                  )
               );
               return;
            default:
               throw null;
         }
      } else {
         Iterator var4 = List.copyOf(var3).iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s1yuzy4vnwa58x","Jt+4GXaAHO5P85xK1dQdwow08+Y2hIVtgX/08gKrJMU=",-331407216787470570,5165897161166619312,-9004012610490511659,3097297878031185815>()) {
            case -2056766130:
               while (var4.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"sy9syzejlbxzy","G1ZjchHF0BUEVtucPUdma3IqEFhJYc+D6dzfRrfsrOM=",-598152204200557826,-3953011432367673787,6986275428031009659,2886740716553456382>()) {
                     case 1906455956:
                        Block var5 = (Block)var4.next();
                        if (!this.h(var5)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s296tpr6crbt3w","RQ0mVEbRpADC1xSZeReCVk3ICZSonRvLEPXI/MYBXRw=",8688820574864891584,-5598884277445383581,-5835024774312833351,991220683932900283>()) {
                              case -1423996890:
                                 switch ((int)com.yiyiaddon.m.b.a<"s2fly87ugdvaoa","uZUCIppo2KKLIK72QzHBJsSKC08vHAmyD4CWP4IyRzU=",8801962990187012216,3546765082730532754,-7064071153934668562,-3600896523394729987>()) {
                                    case 1465450862:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           com.yiyiaddon.l.j.b var6 = new com.yiyiaddon.l.j.b(
                              (String)com.yiyiaddon.m.b.a<"s2gb23l230nf4x","hGqXHOLI5fVOXTgI04aPdv9Av6X0Lo9TD5/SPXUg",-3550137107353070207,-6541407467166498077,-4798043380184027935,7269088242739904173>(),
                              () -> {
                                 this.aJ.get(this.i).remove(var5);
                                 this.jg();
                                 this.u();
                              }
                           );
                           var6.a();
                           var1.a(this.a(var5).a(var6));
                           switch ((int)com.yiyiaddon.m.b.a<"shzh3n1f1pr1w","OWHtY4e/XQCbqzcF2PhH8UfQxMc4KXs5o4euR3TNjo4=",-7326238479888450773,-6185122411502163484,7340895342882997354,8361197797396393308>()) {
                              case -1229315637:
                                 continue;
                              default:
                                 throw null;
                           }
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

   private void je() {
      Minecraft.getInstance()
         .setScreen(
            com.yiyiaddon.l.h.g.a(
               (String)com.yiyiaddon.m.b.a<"s1951wsgnnny0a","9cZuXc2jA1s71kTSCsWU9PmJUssodtvfo4GeiPS+5+Nj2r/QZgc=",6342048632181241466,2824462318111549343,-6125126896262418102,342060548332364399>(),
               this,
               com.yiyiaddon.f.a.c.a(c.c.BLOCK),
               var1 -> {
                  Block var2 = com.yiyiaddon.f.a.c.a(var1);
                  if (var2 == null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1um2b4vi2yw7t","XVU4c2aI8JXfcU9tejKSJdsLJgUeLUJtG2ykcSZCIKA=",-7193381792417239053,-1356512758238714470,2484184855409337956,9114134731198374617>()) {
                        case 1659601846:
                           return;
                        default:
                           throw null;
                     }
                  } else {
                     this.aJ.computeIfAbsent(var2, var0 -> new ArrayList<>());
                     this.i = var2;
                     this.jg();
                     this.u();
                  }
               }
            )
         );
   }

   private void jf() {
      if (this.i == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3fkmakqlm0r7x","FowR1V8C0GavwEPvM0Jvzu9iH/M9Pju3OWhryAAE2zc=",-8063607669892550286,9164042706657069849,1099284750332056866,-5571401383356943297>()) {
            case 94362849:
               com.yiyiaddon.d.c.a(
                  this.BK,
                  (String)com.yiyiaddon.m.b.a<"s192d7n1htvuw","/z3x3c64exd6UQcGJghPHWMiGgl6q9qS67y6p41Hj/Q8XYPiQVm+iN4/Vhzi6zO5ySLGbUTMQRvkenk93aBwzwdO",197889221763021413,4446122283635532085,4991546444094516779,-406582860925391353>()
               );
               return;
            default:
               throw null;
         }
      } else {
         Block var1 = this.i;
         Minecraft.getInstance()
            .setScreen(
               com.yiyiaddon.l.h.g.a(
                  var1.getName().getString() + "",
                  this,
                  com.yiyiaddon.f.a.c.a(c.c.BLOCK),
                  var2 -> {
                     Block var3 = com.yiyiaddon.f.a.c.a(var2);
                     if (var3 == null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s22p1wed4qklit","aWLgG2oEqiQL74kouizO5spYgwDU7G4YNIMHX8XaSSg=",-3109312463137551266,-3164465573121099951,1644699245799807005,5282928528760537360>()) {
                           case 1559167261:
                              return;
                           default:
                              throw null;
                        }
                     } else {
                        List var4 = this.aJ.computeIfAbsent(var1, var0 -> new ArrayList<>());
                        if (!var4.contains(var3)) {
                           label18:
                           switch ((int)com.yiyiaddon.m.b.a<"szb5dntlysu0x","A22fkUD4fnK3N+p3yaPEA7agx7B6BlIcCoGZHql1i8g=",-987871078465607419,-2345923455538086751,1177412866448070001,-9215207583614741692>()) {
                              case -1006600851:
                                 var4.add(var3);
                                 switch ((int)com.yiyiaddon.m.b.a<"s1w0il63xl6po4","G9jHYK3ryTPCk4OCcbNdz/N850/SFb9S7A6VZgXcAsY=",7141202987280648276,-3179568810798936623,-6149630455503960553,-3223655422365796143>()) {
                                    case -152178224:
                                       break label18;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        this.jg();
                        this.u();
                     }
                  }
               )
            );
      }
   }

   private n a(Block var1) {
      Identifier var2 = BuiltInRegistries.BLOCK.getKey(var1);
      String var10000;
      if (var2 == null) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"s2jxqn5v5qdh0s","jDlxYhG52g6lFhJuklevFlioSzSafbCcdcrlon6q63Q=",856554112430860296,-3597804269105387524,2007096720858241908,-7647844922728591923>()) {
            case -284193108:
               var10000 = (String)com.yiyiaddon.m.b.a<"s1l0384ekhlsjm","ZL353+bRSNckWFOIX5WnmQh9cYduacJj523PjqMR",-565617015820701033,-6891469453671081867,6022218276420081071,8486058509230285763>();
               switch ((int)com.yiyiaddon.m.b.a<"s3ss6whnha1et4","D4L8PkuwYOXUkCa82n0S2bwmfv9YOdQLG76gjdwHGeE=",-336092943702549803,-184006740199265790,-4069577872758226838,-1778919161211940388>()) {
                  case 1884577353:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = var2.toString();
         switch ((int)com.yiyiaddon.m.b.a<"s35cob6g22i9zt","6hpI4y50zKGGwfEL1xZgoC5DiOyGBBrIb+85PMKIf+I=",-8617445965067601217,2449313073785036617,-8311120270967444704,-6388343038369937732>()) {
            case 1416379941:
               break;
            default:
               throw null;
         }
      }

      String var3 = var10000;
      return new n(var1.getName().getString()).a((var1x, var2x, var3x, var4) -> com.yiyiaddon.l.g.c.a().a(var1x, var1, var2x, var3x, var4)).a(() -> var3 + "");
   }

   private boolean h(Block var1) {
      if (this.vg.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2xgtl9m7ufx5q","I1biAWP+2ajUa10EwA0HC7sW7Cq6LD9k+byS07hFHts=",5856574868347231302,3495470335679723983,-254323117371947255,4227155391446360253>()) {
            case 1796499590:
               return true;
            default:
               throw null;
         }
      } else {
         Identifier var2 = BuiltInRegistries.BLOCK.getKey(var1);
         if (var2 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s47rjicjev1rq","KxNGf9Shk3v/pRJ2tGdrZH6JqdKs3l65QnhWeN3p/GM=",-4787971422136876885,-5164238186708648771,4193606522247097350,2569644100853576777>()) {
               case 610235490:
                  if (var2.toString().contains(this.vg)) {
                     switch ((int)com.yiyiaddon.m.b.a<"sct84lmnr315a","A4jR7ImE/L6Ny/uCuLWHRUjUwPsFzsR6FFkLevcF2Gw=",1582587287770372031,-1338022315567268724,7944207576528635816,5547682343738558569>()) {
                        case -1191286527:
                           return true;
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         return var1.getName().getString().toLowerCase(Locale.ROOT).contains(this.vg);
      }
   }

   private void bd(String var1) {
      String var10001;
      if (var1 == null) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"s2f2peepm1cxei","Ih5ciyB6TEO1vMEj64PqdE3BBoQB3Le71mlgdfoRF+k=",-7018355906448783917,-1373816538729052085,8086939845987653388,-24857867818634278>()) {
            case -297168190:
               var10001 = (String)com.yiyiaddon.m.b.a<"s31wcc02wdfhx6","AY0TLLOw4Xaihq6UoqX6vpJok03wi8xnZ1zimQ==",-1196055475851078419,8555190255124303770,-108331198122800907,1598930206529945408>();
               switch ((int)com.yiyiaddon.m.b.a<"s2zp8sdsdx2gvs","3FrIuN6xsz7aGC/vgHHyWBXObjrxLD7TEmYIzwmBcqw=",-6016684844448574109,-1575854517709722192,8735027202930263916,8956306976292796934>()) {
                  case -165102529:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = var1.strip().toLowerCase(Locale.ROOT);
         switch ((int)com.yiyiaddon.m.b.a<"s2pbuomgs4xq73","6R/RLuRPVHF82Vay9IgJLPC2EqbPTKy73JRwITWw5rw=",-3682288427700041234,-1352873160099845343,7615395303429288234,-2046039025854364415>()) {
            case -1914235438:
               break;
            default:
               throw null;
         }
      }

      this.vg = var10001;
      this.u();
   }

   private void jg() {
      this.j.accept(new HashMap<>(this.aJ));
   }
}
