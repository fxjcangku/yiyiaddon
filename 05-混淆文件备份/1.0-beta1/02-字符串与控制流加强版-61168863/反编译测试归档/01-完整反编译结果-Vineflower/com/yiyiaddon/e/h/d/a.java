package com.yiyiaddon.e.h.d;

import com.yiyiaddon.g.c.d;
import com.yiyiaddon.i.b.c;
import com.yiyiaddon.m.b;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public final class a {
   private static final int eJ = 120;

   private a() {
   }

   public static com.yiyiaddon.e.h.c.a a(boolean var0) {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.player == null) {
         switch ((int)b.a<"s3navgedyivyk1","tQqZoKJhVZvyR64fQyCbmomwg+zXCc37ADFUrVeECDo=",-4503394236008913069,-9168707523719454491,-3564988552279346910,-5102638900944156765>()) {
            case 1578971133:
               return com.yiyiaddon.e.h.c.a.a(
                  com.yiyiaddon.e.h.c.a.a.ITEM,
                  (String)b.a<"s205d7vzpbs58c","pHFGRG3gqZn+L2oz1nrukoOZBCpItRD3nG3Cm8H4b1yJdPf0Agg=",380833949918167514,-7955479921122012408,8221568673957840679,4639345518127131460>()
               );
            default:
               throw null;
         }
      } else {
         ItemStack var2 = var1.player.getItemInHand(InteractionHand.MAIN_HAND);
         if (var2 != null) {
            switch ((int)b.a<"s2ts651rbfr657","dgFqZL+YGh/BG75SW+g/G+o0IyfVdzQ3JfeCJDXrFuo=",-3246543504550112625,6225589464661194520,725823474200966150,3722282798671498839>()) {
               case 850576576:
                  if (!var2.isEmpty()) {
                     d var3 = c.a(var2);
                     if (var3 == null) {
                        switch ((int)b.a<"s2jqadlnpbnqe8","K6Bd8XEzB0V31Wo8hkWJXmqhicQxZUZgryEp0HyDDW8=",-7900913005007227848,-9125307455634875013,-788715406744087203,8066208133875071672>()) {
                           case 91615110:
                              return com.yiyiaddon.e.h.c.a.a(
                                 com.yiyiaddon.e.h.c.a.a.ITEM,
                                 (String)b.a<"s1xednbqcil3pi","ihHo83TCQ+Z5Oxr3zZA6q2tgVpzYRSCIJzd5Tiqhc5EEpauXUlHvTJBI91VEmOIEs4DUcA==",3654033315617429474,-3518286816234799578,-4733562246968406717,-1313417387496647678>()
                              );
                           default:
                              throw null;
                        }
                     } else {
                        String var10000;
                        if (var0) {
                           label92:
                           switch ((int)b.a<"s3spfn03c1w5v3","WCm7GSTjX8fxulQOjBd1k8uz3APaYhifMgEUerQKq+M=",-8321892984467229870,-9147535580404138070,-8047827390512142883,-8053753154249224215>()) {
                              case -216683663:
                                 var10000 = com.yiyiaddon.k.b.a.a().f(var3);
                                 switch ((int)b.a<"s15qjaopc3hfhm","ilKY791HZMFb2rsJs63YARjmpmigDtptS2fOa7jMHBU=",-7180765645716206010,-20007990067705797,2802458817627156939,-34117529101906318>()) {
                                    case -1524716018:
                                       break label92;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           var10000 = null;
                           switch ((int)b.a<"scwxointmnn8u","Z8UwAIgxN+uMm16oAy2SYZf/eCbl5A7yxDwKjneOiXI=",3386483990940012466,-3949077723562305914,1307824271182723046,6885847492201108510>()) {
                              case -610080791:
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        String var4 = var10000;
                        ArrayList var5 = new ArrayList();
                        var5.add(
                           new com.yiyiaddon.e.h.c.a.b(
                              (String)b.a<"s1ct3mo2i7y21o","kZdh7x40JIdCjDb2HbDUzrYXEv0UwG5Cop6rfntRcc8=",1963075206016713243,-4618638554747238435,5707486879833717561,-204903025164267403>(),
                              var3.m()
                           )
                        );
                        var5.add(
                           new com.yiyiaddon.e.h.c.a.b(
                              (String)b.a<"s3sff89j6j0wze","dVN+P1ok3RkE/sKo8Dosj1wQJeToPiF39tvv1TdLh+qsB5PxgDQ=",-6933726238940457323,-4078805949524537041,-4212476648263514460,2661703856775587933>(),
                              var3.be()
                           )
                        );
                        var5.add(
                           new com.yiyiaddon.e.h.c.a.b(
                              (String)b.a<"soejlhihlof3l","db5BVVVTT3LLb8tDlv6nNFhfOVIPoAPoGgEevl9RsLF0KfNc",-6755574610484239653,2091849313962276830,-890678767082835928,-706439480201203063>(),
                              var3.dw()
                           )
                        );
                        if (var3.fZ() != null) {
                           label87:
                           switch ((int)b.a<"s29i4ee5vumm0q","akCct35BTjz2FyAeRcb69gyWScFr6j+453vDd52UYDY=",7044000502420004932,-6051494834040426807,6069909225079083160,3003849860272120837>()) {
                              case -58892964:
                                 var5.add(
                                    new com.yiyiaddon.e.h.c.a.b(
                                       (String)b.a<"sqc4ncmb7makm","sPXgsrgt5x6ZAfP/Mx8+GCEjKjdMLaEDYejHSDb1k3qrePKGnyw=",8051651878833434989,1738472196612318555,4165159568585984420,-1815426653202304857>(),
                                       var3.fZ()
                                    )
                                 );
                                 switch ((int)b.a<"sic9a6okvfa7k","d8AF1bV+d84Ja2jt+3eKrmCGOOud/MbQXS/HCc1MW+I=",-4040087725112579219,1411584497337084241,-2087854287739652229,4437443952451562701>()) {
                                    case -769608756:
                                       break label87;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        if (var3.dE() != null) {
                           label82:
                           switch ((int)b.a<"s3ti06df2r4vbl","r9fZjRTvHikrR+0EVfJV65ZSPZqvlBzGSM1yrbtwRPk=",-3127878993146149900,-642861717195278832,4817230609993635828,-8095156640756056622>()) {
                              case 947609460:
                                 var5.add(
                                    new com.yiyiaddon.e.h.c.a.b(
                                       (String)b.a<"s3bo5zap4fmcxj","IhZc7SINAMxaig6SfY/OaKyha9cjl/ck1kH48B9Qi16cN66M",-6294430566466649094,-9025518128425363753,2676261729916534483,4452932242402350559>(),
                                       var3.dE()
                                    )
                                 );
                                 switch ((int)b.a<"s1smdwmotoc4rt","0m/93lZdl3m4mZ7vTHo2oLgLidqOkFUZoI8GT5C9F04=",1472226065771626613,-6940281209727704721,-8753080213664267656,-5068590053338756095>()) {
                                    case -213991842:
                                       break label82;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        if (var3.fe()) {
                           label77:
                           switch ((int)b.a<"s387bw1agvu3jx","4XIGt75fKcm+04dmoGWxvfVGNq/lO+O3AykpbI0diFk=",4469337908068434495,7396299899001415604,-2545754206303351616,-3718559360739478825>()) {
                              case 985662419:
                                 var5.add(
                                    new com.yiyiaddon.e.h.c.a.b(
                                       (String)b.a<"s2leiqang6d9oq","lnALb8q72HZpfnSX4DG3Edw1kPcPUQN0gbS59u8AAqOO/0pl9lQ=",-7642349460239815883,-7287174741951331495,7862448761410611232,-6258358187031707949>(),
                                       var3.fW()
                                    )
                                 );
                                 switch ((int)b.a<"s3jmtlcmyue4hv","YkL6yax1ZCDSQKsof6FALc+IDEA6qu3sycwfRO0ozBs=",6163878858868407169,-5956702285952395483,-4574057456363314625,3659902247225754318>()) {
                                    case 283899280:
                                       break label77;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        if (var3.fY() != null) {
                           label72:
                           switch ((int)b.a<"s2bb58w0jjseuz","3gZd3uEabhmn9y8dFfzY79xiDMfHnaGnlx5dqFj/Sew=",-4024026919508072950,3129694491528358338,9086046371415342364,-5140839033098017325>()) {
                              case 1803127757:
                                 var5.add(
                                    new com.yiyiaddon.e.h.c.a.b(
                                       (String)b.a<"s1cvnjym86ei3y","EqVTcnGhT5lg7+E5A9VPpjw6expiGmnj/k6B6ZuCxfWEOj6H",4801924088400636748,-7983010063340452588,-4038166330558169328,-6066756490005756631>(),
                                       var3.fY()
                                    )
                                 );
                                 switch ((int)b.a<"s1alaw36rzhpuq","LzkZGc9tWF6XzJV0zDMT5ZMkPUV20dMDBujUitTQAZc=",-8074368996847802191,3987008713922915289,6513620670465711447,-7964394367344377322>()) {
                                    case -495140938:
                                       break label72;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        if (var3.fh()) {
                           label67:
                           switch ((int)b.a<"s3626jclvrv7uo","3bUvwNjbUeUz8jKSjrect4Evb7ghdyMiUrwU2qijweg=",-5332247945897231156,330331655829956675,-711480613274918488,1912092550652966583>()) {
                              case 1963697588:
                                 var5.add(
                                    new com.yiyiaddon.e.h.c.a.b(
                                       (String)b.a<"s1r19832wt51ai","Ca3t85gMZ3/w+dK3uAe9ZAE+dtb0OfWIbnpuNl3fcro=",1982096683845994586,5202266578547085631,5619278407705624411,-2066681385864723810>(),
                                       c(var3.bw())
                                    )
                                 );
                                 switch ((int)b.a<"s1w5zatokhr4sb","0xQcZflc5BsW+yD8fCxJQ51mrHm9ik7BLFdTm058Ot0=",3528584189488112104,-1480320756105880888,-5186199018946833867,-6469070251348952213>()) {
                                    case -556688879:
                                       break label67;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        if (var3.M() != null) {
                           label62:
                           switch ((int)b.a<"s2vwqmpx7qpizi","8kBdkBR927LPV7sMKRu89VAxjsP8e7v8B3r1M17O4Aw=",-7814287408011505,-1071284278923935342,6580536089002581451,4332662496396995127>()) {
                              case -1125585504:
                                 var5.add(
                                    new com.yiyiaddon.e.h.c.a.b(
                                       (String)b.a<"suqinfxifkzqb","6TA+mTETaGL41Ynk8Uy743GH49Hu4zfBhSvY4bteTGNAsu7G",-3301646792811250495,9175368071433667579,-5571301095101960378,-7076640816635375152>(),
                                       String.valueOf(var3.M())
                                    )
                                 );
                                 switch ((int)b.a<"s23z096yrenvs2","DBAUf2qgPcEGP00tAKLIZ/MHSF+1yDllvjQ7VaIpszw=",2421614279656650824,4517280969253145892,8453441148335691356,-3856626699681319918>()) {
                                    case 79381048:
                                       break label62;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        var5.add(
                           new com.yiyiaddon.e.h.c.a.b(
                              (String)b.a<"s2ws6nbqqz5c68","XTzsnelCMxjEfu2SaRfiVfkjditWenTBMtIgwhxT5/E=",-2388882811964879894,-859937582212402066,-3835535807550954153,8544284056690276195>(),
                              String.valueOf(var3.dw())
                           )
                        );
                        var5.add(
                           new com.yiyiaddon.e.h.c.a.b(
                              (String)b.a<"s2um5hns4hv6fa","v9zirwCs5xb7E8x/TBFD7r8uqwL9AJaSc8hHvyg8EzSG0w==",2034823110115731752,-3081940207297600986,6643752556375605307,-7541857954101836274>(),
                              var3.dF()
                           )
                        );
                        return com.yiyiaddon.e.h.c.a.a(com.yiyiaddon.e.h.c.a.a.ITEM, var3.m(), var5, var4, null);
                     }
                  } else {
                     switch ((int)b.a<"s18u8f1kgrmonu","Pbk9GTUVK7QnEstBvtvh+oM49YNXmY3nq9uDfE4H6gE=",7419212279728901629,-4462642445525691978,5458784572406138906,6769927178457391863>()) {
                        case -258685262:
                           return com.yiyiaddon.e.h.c.a.a(
                              com.yiyiaddon.e.h.c.a.a.ITEM,
                              (String)b.a<"stmgon13ero5q","kVGj5SIKhoK+S7rUQKHz2jbranSx0K/HB0zFhTGz8jfMA8eV1qGlRwVI/K5AdbAzDrB6gv/YybFvs58usZ4=",-3715119547272037744,1711322303555625450,2692168482578120176,2730839522081056990>()
                           );
                        default:
                           throw null;
                     }
                  }
               default:
                  throw null;
            }
         } else {
            return com.yiyiaddon.e.h.c.a.a(
               com.yiyiaddon.e.h.c.a.a.ITEM,
               (String)b.a<"stmgon13ero5q","kVGj5SIKhoK+S7rUQKHz2jbranSx0K/HB0zFhTGz8jfMA8eV1qGlRwVI/K5AdbAzDrB6gv/YybFvs58usZ4=",-3715119547272037744,1711322303555625450,2692168482578120176,2730839522081056990>()
            );
         }
      }
   }

   public static com.yiyiaddon.e.h.c.a b(boolean var0) {
      com.yiyiaddon.g.c.a var1 = com.yiyiaddon.i.b.a.a();
      if (var1 == null) {
         Minecraft var5 = Minecraft.getInstance();
         String var6 = var5.player == null
            ? (String)b.a<"s205d7vzpbs58c","pHFGRG3gqZn+L2oz1nrukoOZBCpItRD3nG3Cm8H4b1yJdPf0Agg=",380833949918167514,-7955479921122012408,8221568673957840679,4639345518127131460>()
            : (String)b.a<"s2et9xznjh2mcx","C/1tTelnUTx3yDZVfQKA/JynBExyOcRZmMHhNoIGcAiVvLHcffbA0PGOsZDtb5K6l2Jx+w==",-8654602758573790989,1350120553747843627,4932871069823207565,-5242571580282416867>();
         return com.yiyiaddon.e.h.c.a.a(com.yiyiaddon.e.h.c.a.a.BLOCK, var6);
      }

      String var2 = null;
      String var3 = null;
      if (var0) {
         com.yiyiaddon.k.b.a var4 = com.yiyiaddon.k.b.a.a();
         var2 = var4.e(var1);
         var3 = var4.b(var1, false);
      }

      ArrayList var7 = new ArrayList();
      var7.add(
         new com.yiyiaddon.e.h.c.a.b(
            (String)b.a<"s1ct3mo2i7y21o","kZdh7x40JIdCjDb2HbDUzrYXEv0UwG5Cop6rfntRcc8=",1963075206016713243,-4618638554747238435,5707486879833717561,-204903025164267403>(),
            var1.m()
         )
      );
      var7.add(
         new com.yiyiaddon.e.h.c.a.b(
            (String)b.a<"s2zir2qgkb8yt8","YSOMCr8ucDiSgf+oUYqy+7h4O8boOt6YkWclPKoaX8rbql7pCrI=",553133024981395108,-4777174976745803850,-8871558628517800273,-613437768511469161>(),
            var1.fL()
         )
      );
      var7.add(
         new com.yiyiaddon.e.h.c.a.b(
            (String)b.a<"sddx0cipsgjgw","M+FsAylOgVcP+NPBfE5Fq2oStX2DbRXF9MVctb8vIruzULM7",-7329780029552217839,-925619855213705690,-7467367974516851489,7923560831196187718>(),
            var1.fK()
         )
      );
      var7.add(
         new com.yiyiaddon.e.h.c.a.b(
            (String)b.a<"s3btk51z0dxvdq","yEqAulHDBIGlJZQTxmAYGSO2ymjoCT9Cre1LSR+w+UI=",820069591476706746,1203032776436895448,-889962570104210333,-9034882783326511638>(),
            "" + var1.aj() + var1.ak() + var1.al()
         )
      );
      var7.add(
         new com.yiyiaddon.e.h.c.a.b(
            (String)b.a<"s3uh3p83gujh7c","WmN9vqEHpM42eQ0gMzKO+NzCTbBPo5wXw/5KbmkNLWE=",5050988178956141817,-2027987227778550899,-2553008532071922698,-1094804566734240756>(),
            var1.bU()
         )
      );
      var7.add(
         new com.yiyiaddon.e.h.c.a.b(
            (String)b.a<"s289uzeeafxm6d","1ZvZTyGvqNWpcm59vPtMqr2dLn2yZEhKl9no2RPPZStQeQ==",9194297318657953054,-1571692796976947632,-5484815508781705036,2632182127511512759>(),
            var1.fG()
         )
      );
      if (var1.fO() != null) {
         var7.add(
            new com.yiyiaddon.e.h.c.a.b(
               (String)b.a<"s3ts6ufh34xlbv","vk8zIiL4zFbSRIjbOQINxA7lffXrqUUCQwV/327XHAS1IoJdmC6Yqg==",8570432200579447092,-6204077723899089773,8242158462648027528,-1003381336323087716>(),
               var1.fO()
            )
         );
      }

      var7.add(
         new com.yiyiaddon.e.h.c.a.b(
            (String)b.a<"s1m8jrai4k7b07","L8lWbgtDa/fNFGgBz2FlClYQlkC69LryzEejN53wfcQUD0Wv",-1678962291991943042,8694670522724794626,-2858846432949895754,615054453705456742>(),
            var1.fS() == null
               ? (String)b.a<"s1sykbpp68zwqi","pmzG2Nw8ivka3QZs7kctG/KdGa+6DLc7yx3jO0ad7ToMtQ==",-3981411549789799354,797723994164417108,-6882788503664883851,-3703196509302008388>()
               : var1.fS()
         )
      );
      if (var1.dr() != null) {
         var7.add(
            new com.yiyiaddon.e.h.c.a.b(
               (String)b.a<"sqc4ncmb7makm","sPXgsrgt5x6ZAfP/Mx8+GCEjKjdMLaEDYejHSDb1k3qrePKGnyw=",8051651878833434989,1738472196612318555,4165159568585984420,-1815426653202304857>(),
               var1.dr()
            )
         );
      }

      if (var1.ds() != null) {
         var7.add(
            new com.yiyiaddon.e.h.c.a.b(
               (String)b.a<"s3bo5zap4fmcxj","IhZc7SINAMxaig6SfY/OaKyha9cjl/ck1kH48B9Qi16cN66M",-6294430566466649094,-9025518128425363753,2676261729916534483,4452932242402350559>(),
               var1.ds()
            )
         );
      }

      var7.add(
         new com.yiyiaddon.e.h.c.a.b(
            (String)b.a<"s2um5hns4hv6fa","v9zirwCs5xb7E8x/TBFD7r8uqwL9AJaSc8hHvyg8EzSG0w==",2034823110115731752,-3081940207297600986,6643752556375605307,-7541857954101836274>(),
            var1.dF()
         )
      );
      return com.yiyiaddon.e.h.c.a.a(com.yiyiaddon.e.h.c.a.a.BLOCK, var1.m(), var7, var2, var3);
   }

   public static com.yiyiaddon.e.h.c.a c(boolean var0) {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.player == null) {
         switch ((int)b.a<"s2vesbevo5weig","hYk3EbD0MA5m58N5rCBdMjkap43r1LrJKcN66BCGgXk=",4467112114005943496,8706580615811356624,619276306674900802,7701062785442776468>()) {
            case -433426867:
               return com.yiyiaddon.e.h.c.a.a(
                  com.yiyiaddon.e.h.c.a.a.ENTITY,
                  (String)b.a<"s205d7vzpbs58c","pHFGRG3gqZn+L2oz1nrukoOZBCpItRD3nG3Cm8H4b1yJdPf0Agg=",380833949918167514,-7955479921122012408,8221568673957840679,4639345518127131460>()
               );
            default:
               throw null;
         }
      } else {
         Entity var2 = var1.crosshairPickEntity;
         if (var2 == null) {
            switch ((int)b.a<"s2xcmrc8z3wl5j","4Bcn1hYoLq9NJxGdej17yJCl3mj9IAGwmUsgrtS8Xv4=",2359700964130945681,-7177915795330394205,8390219353897643487,8657176193926758225>()) {
               case 2075978654:
                  return com.yiyiaddon.e.h.c.a.a(
                     com.yiyiaddon.e.h.c.a.a.ENTITY,
                     (String)b.a<"s1zq3gl6k8ijjg","bWWTzXXXGTQREUC1dZ7vNKvRsqbaj2H6j95r1LWiLPZVar0MJ6O3OB8tjlE90upkA4ki6w==",-6447373120844125656,5732321960138427925,2548998845094565135,-5370184582747852876>()
                  );
               default:
                  throw null;
            }
         } else {
            com.yiyiaddon.g.c.b var3 = com.yiyiaddon.i.b.b.a(var2);
            if (var3 == null) {
               switch ((int)b.a<"sq5dh6rniufdi","I08deR0TNgOQo8k8LJSYphY7owywO0rQE4qPlDTr5R4=",4610301314089262424,-2665993109138501632,-5413475000190072717,5186577298417399623>()) {
                  case -831437933:
                     return com.yiyiaddon.e.h.c.a.a(
                        com.yiyiaddon.e.h.c.a.a.ENTITY,
                        (String)b.a<"s3nva2dms5w8a7","vt0qKDChzJ6zLFLuRbBbRZA1IB/96UK40jV/cQ8FYA8nLvwxozJhJjT7APAXXF/0L/0vNw==",2471381117651951936,-715296745042012313,-2850554902044418495,-7672973832436952715>()
                     );
                  default:
                     throw null;
               }
            } else {
               String var10000;
               if (var0) {
                  label50:
                  switch ((int)b.a<"s26uaucmon1lpd","eTZA5xGWN95d5FqHxL0Z16AL3AT2oxGj/8WavKQfYNQ=",-4641363472426472479,-3255617486496607081,-4094122623836539043,-5120021867012698094>()) {
                     case 1329778811:
                        var10000 = com.yiyiaddon.k.b.a.a().e(var3);
                        switch ((int)b.a<"s29larni9wsjg0","6dmsQ+7KrApa6wUiag1qVtvU+sN0o79Q/LXA8SKARQU=",-5640847057956841477,8544249513526134690,-1290722178782640653,-4450400963638525330>()) {
                           case -1289559065:
                              break label50;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var10000 = null;
                  switch ((int)b.a<"s2dtq2aqdqahq3","pr2WTMPQKYfPsObjoYX2QgIkGuBgk7qLyC0XzAph/ag=",6284433267020530319,-2610092442787553083,-2676662228754141375,-2574427932898044482>()) {
                     case 1842001861:
                        break;
                     default:
                        throw null;
                  }
               }

               String var4 = var10000;
               ArrayList var5 = new ArrayList();
               var5.add(
                  new com.yiyiaddon.e.h.c.a.b(
                     (String)b.a<"s1ct3mo2i7y21o","kZdh7x40JIdCjDb2HbDUzrYXEv0UwG5Cop6rfntRcc8=",1963075206016713243,-4618638554747238435,5707486879833717561,-204903025164267403>(),
                     var3.m()
                  )
               );
               var5.add(
                  new com.yiyiaddon.e.h.c.a.b(
                     (String)b.a<"s31i3c5vrgovbi","P9P64wkAJ2jCbMAkGIz/IwdDUyooNDP4QerbdrVgRlLOFIi/qQk=",3991991758996444523,3186741751949553192,1170663733003569325,-6785184533033925055>(),
                     var3.fU()
                  )
               );
               var5.add(
                  new com.yiyiaddon.e.h.c.a.b(
                     (String)b.a<"s1cvnjym86ei3y","EqVTcnGhT5lg7+E5A9VPpjw6expiGmnj/k6B6ZuCxfWEOj6H",4801924088400636748,-7983010063340452588,-4038166330558169328,-6066756490005756631>(),
                     var3.fV()
                  )
               );
               if (var3.fd()) {
                  label45:
                  switch ((int)b.a<"s2fbbn2xov2osl","f8QUb/aABBWyui+/wWhR+Lv9XioDL/JCHJU0qM84FR4=",-4150040659744762889,795531942847207887,6809420686980062244,-609525790353269653>()) {
                     case -301573275:
                        var5.add(
                           new com.yiyiaddon.e.h.c.a.b(
                              (String)b.a<"s2leiqang6d9oq","lnALb8q72HZpfnSX4DG3Edw1kPcPUQN0gbS59u8AAqOO/0pl9lQ=",-7642349460239815883,-7287174741951331495,7862448761410611232,-6258358187031707949>(),
                              var3.fW()
                           )
                        );
                        switch ((int)b.a<"samtwdbfaboxs","lFcfbpMhwHJ+hi3vgbe8m3NIgB5AwYB3UVlObFZLPkI=",-3946434030220406310,-5514381222628573376,-1218282851401019842,1490002823411240001>()) {
                           case 13979808:
                              break label45;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               if (var3.fX() != null) {
                  label40:
                  switch ((int)b.a<"s2i1pzpasbn5m3","RFW/FqaiyHdnoBkjyr8/rVFnLD8ggdZmnIBG9WW/8qs=",8985138638372991770,5704288575895252476,-1950253094460719390,-8225345518432037156>()) {
                     case -176556646:
                        var5.add(
                           new com.yiyiaddon.e.h.c.a.b(
                              (String)b.a<"s3lrr5obnyabh9","8rAezZM5BGnuLw9jLiudhL3RVxXigfIV8diA2GRG/Qq9k09x",5159741615835924840,-3071808187603111540,-5767207678732590992,944014904626709324>(),
                              var3.fX()
                           )
                        );
                        switch ((int)b.a<"s3gniq0yal8fzy","wbNPJjv0f26KA7Q2RxhXKnsMfOcmff4oxmKlBTBKNnw=",-8203431554804676741,-1932581863833027956,8655074852012487868,8318028985307531897>()) {
                           case 343725933:
                              break label40;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               var5.add(
                  new com.yiyiaddon.e.h.c.a.b(
                     (String)b.a<"s3btk51z0dxvdq","yEqAulHDBIGlJZQTxmAYGSO2ymjoCT9Cre1LSR+w+UI=",820069591476706746,1203032776436895448,-889962570104210333,-9034882783326511638>(),
                     "" + var3.dt() + var3.du() + var3.dv()
                  )
               );
               var5.add(
                  new com.yiyiaddon.e.h.c.a.b(
                     (String)b.a<"s2um5hns4hv6fa","v9zirwCs5xb7E8x/TBFD7r8uqwL9AJaSc8hHvyg8EzSG0w==",2034823110115731752,-3081940207297600986,6643752556375605307,-7541857954101836274>(),
                     var3.dF()
                  )
               );
               return com.yiyiaddon.e.h.c.a.a(com.yiyiaddon.e.h.c.a.a.ENTITY, var3.m(), var5, var4, null);
            }
         }
      }
   }

   public static int aL() {
      return com.yiyiaddon.c.a.a.a(com.yiyiaddon.k.b.a.a());
   }

   private static String c(List<d.a> var0) {
      ArrayList var1 = new ArrayList();
      Iterator var2 = var0.iterator();
      switch ((int)b.a<"s3bqhaxbjuaxp5","ShqRB3EJoYi/zYUcFurqFLOWc5MmUEaZ85+xS6FDxmc=",-6105750108639216965,5377827317241287304,4615958201728454158,5758748109572409114>()) {
         case 1515608029:
            while (var2.hasNext()) {
               switch ((int)b.a<"s36q9cc1iedqsd","6Qzhb1ESSahVQAbipnq7ZQ+IJENa3Omrd1yTnMZcZt4=",-4818751854849470165,4271077469250888397,-3059101669301314257,-5551745820214295648>()) {
                  case 1084149049:
                     d.a var3;
                     String var10000;
                     label45: {
                        var3 = (d.a)var2.next();
                        if (var3.m() != null) {
                           label30:
                           switch ((int)b.a<"s3pr8dy9053ji3","kyBBlyqTGOeKFqqTQuBPypgsk6GbO45a8IaK8OylJiU=",-4243478407200148863,8209920946451259205,-4486302299183327240,4727718104299603789>()) {
                              case 313937562:
                                 if (!var3.m().isBlank()) {
                                    var10000 = var3.m();
                                    switch ((int)b.a<"s1uszz0usdh3hg","dEBI55ECwX28H3f5DGtguPnKbEJ2eKKjYoCz5QBSma0=",6148774969651441495,4705242325128712926,-3659247117739462913,-161676589255074449>()) {
                                       case -1762003778:
                                          break label45;
                                       default:
                                          throw null;
                                    }
                                 }

                                 switch ((int)b.a<"sjsy2welwaq27","HRaC8ZPYtHEUoP/O+OhDPYQjOINMJPUL2545ah/otB4=",-4679370767116611315,-3972887225614287696,-3488043867079074443,2408669155056925843>()) {
                                    case 1692556106:
                                       break label30;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        var10000 = var3.dA();
                        switch ((int)b.a<"s9nvkvvzgekpk","IpvB2+YGuabovF0YmnVeWmRnjxqonYLsbJ/E56/d3oo=",-2566455970968816857,-5394796371160379018,-1611129800110665059,6558953967895543461>()) {
                           case 1182744124:
                              break;
                           default:
                              throw null;
                        }
                     }

                     String var4 = var10000;
                     var1.add(var4 + var3.ai());
                     switch ((int)b.a<"s2avqpy9o2ci8","zgy3xpAq9ZBxJDL3JjkClwiog2+eCLaJTkpSLA0Ud08=",-1097571277015807003,6731377711270125682,126177353030309184,8182677234164440493>()) {
                        case 2017892658:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return ae(
               String.join(
                  (String)b.a<"s1jnzre2k43oq7","osjHqXIfPYk3nEhEl69Jfj1LW9Gw1RZ51paqmZpc",-1888621713981504113,-8231652359716783738,-8782391969385991703,-7611899486760960731>(),
                  var1
               )
            );
         default:
            throw null;
      }
   }

   private static String ae(String var0) {
      if (var0 == null) {
         switch ((int)b.a<"s3dlu0a9hc7e57","oJsNB+9daMfN7kiFL+tCpEr4YyLqooMl/7BzTpfCj+Y=",2384604113844225026,-6125600136705869355,4770043073532856936,-6930205946909036978>()) {
            case -745672679:
               return (String)b.a<"sn862i12619qa","pqqhGdnWpWv6fvjjGHat7hw7/BepsY0k3s2yww==",1869459103168906481,-1281971336992517374,-3033030502175561912,8578779725895002998>();
            default:
               throw null;
         }
      } else if (var0.length() <= 120) {
         switch ((int)b.a<"s3mr5yh7gmcqho","neecg8HcML73rZ8GOUeQ5r24FrtucW0LrGwk51S6mpc=",-6601505302263599796,-8782700480343304877,-9082815151385448906,-2399615534102097297>()) {
            case 1420947142:
               switch ((int)b.a<"s36tu32updzjuf","N4tFXP5zW0IrhYLe099YDfQQUDFy/G/ZRfbx8yWF0pE=",-5102545548176873647,4300417698267018842,-6913660581684212342,-3516561915643575553>()) {
                  case -1102434247:
                     return var0;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var10000 = var0.substring(0, 120) + "";
         switch ((int)b.a<"s3n4tbbocfrkjo","oO1u2rgKO1QQm3yPf7bTm1TD5akckb2Y1PTbYaJc+fY=",2684867102972525847,1488737038777284349,1925061680465944009,-6409222612226201370>()) {
            case 1748533459:
               return var10000;
            default:
               throw null;
         }
      }
   }
}
