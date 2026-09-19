package com.yiyiaddon.e.n.j;

import com.yiyiaddon.e.n.i.r;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public final class b {
   private static volatile com.yiyiaddon.e.n.j.b.c a;

   private b() {
   }

   public static com.yiyiaddon.e.n.j.b.a a(BlockState var0, r var1) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1ymxmxpdvztla","QI/n/OBxesr4E0vQT6yXcnr3LDg1rhxPjcCAcB/H0gs=",8079189187728951590,-803220259813537560,3110258229788101139,-4088025822475101152>()) {
            case -13766531:
               if (!var0.isAir()) {
                  com.yiyiaddon.g.d.a var2 = com.yiyiaddon.i.e.a.b(var0);
                  String var3 = var2.dv();
                  if (var3 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1dboke7lpe1xy","c2+YnG1Thpy9Plfc7wb1CZC4kFe44ZoZp3RySKQD7r4=",2938124473100721646,-6018332311210513515,-5790128347409446995,-5895498047751184307>()) {
                        case 1458254920:
                           if (!var3.isBlank()) {
                              com.yiyiaddon.e.n.j.c.e var4 = com.yiyiaddon.e.n.j.c.a(var3, var2.a(), var2.ea(), a(var1));
                              return new com.yiyiaddon.e.n.j.b.a(a(var4), var4.dk(), var4.dl(), var3, var4);
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s3phjfqr4dmmcw","NNXaJDVwgqZjU/JrDFdj+K4YMydoXyMFpffLKnNQKOU=",-1211579566666211571,-6764091627244850507,-1902331578434193332,-1680769358822775723>()) {
                              case 926107078:
                                 return com.yiyiaddon.e.n.j.b.a.b();
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return com.yiyiaddon.e.n.j.b.a.b();
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1kc6ax4a7dkou","t+KpOlKahnbMQzbKBwvw7asWHtYzpm2yh2b4qMNsFmo=",2399780243139898765,8440033619657601109,-8959633569308753940,8293484834745074696>()) {
                     case 1919236210:
                        return com.yiyiaddon.e.n.j.b.a.a();
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return com.yiyiaddon.e.n.j.b.a.a();
      }
   }

   public static com.yiyiaddon.e.n.j.b.a a(String var0, String var1, String var2, r var3) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"srm9r54v8g5gz","FOfecDaa++8FeAAhQX0Odr7bXn7mvdnm2d/yIrQt+MA=",-8297943228125644884,5984458520635007837,2558988581433065018,796055959072582728>()) {
            case 814626441:
               if (!var0.isBlank()) {
                  com.yiyiaddon.e.n.j.c.e var4 = com.yiyiaddon.e.n.j.c.a(var0, var1, var2, a(var3));
                  return new com.yiyiaddon.e.n.j.b.a(a(var4), var4.dk(), var4.dl(), var0, var4);
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2m2uk2rt99yn","D/3aKQeUkbFp16VXrcgHy95zIcMnPMWgzjLZo66RYGE=",3287540970987165551,-2277082771472598994,1978140937563275345,1615655349616621384>()) {
                     case 81173668:
                        return com.yiyiaddon.e.n.j.b.a.b();
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return com.yiyiaddon.e.n.j.b.a.b();
      }
   }

   public static com.yiyiaddon.e.n.j.b.a a(BlockPos var0, r var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (var2.level != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s27wkznndnixcf","QGmezpcWTjRbGVjGX+h8u5OE6VnX1J8Yv9BN1AFzbGc=",2122117138747669455,6991159928792448719,-8106239739261827618,2572340944089270022>()) {
            case 473647050:
               if (var0 != null) {
                  BlockPos var3 = var0.above();
                  com.yiyiaddon.e.n.j.b.a var4 = a(var2.level.getBlockState(var3), var1);
                  if (var4.a() != com.yiyiaddon.e.n.j.d.EMPTY) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2uxczavdhnzak","IEj4+ZhovcNYIqXOx+1LNLqomMKqucx2hQUu1cY2IHU=",9098362661627140971,9052724964858835081,5127746393109537425,5972431838217983328>()) {
                        case -1549074181:
                           if (var4.a() != com.yiyiaddon.e.n.j.d.UNKNOWN) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1xweq90hphhln","Zo6GHI2BYinCiRgCLuOSTZMU6V6oG6PwCn3VENZktjk=",-8924260087882525216,-5991409772183139434,-8377904608647842923,6369452707884834724>()) {
                                 case -2129906961:
                                    return var4;
                                 default:
                                    throw null;
                              }
                           }
                           break;
                        default:
                           throw null;
                     }
                  }

                  com.yiyiaddon.e.n.j.b.a var5 = b(var3, var1);
                  if (var5 == null) {
                     label42:
                     switch ((int)com.yiyiaddon.m.b.a<"s1zjyx5upw4ln2","sJOpC1fYxsbeqtnCPekLoyAiw7K1zk0PCwlcbkBlm3I=",7224346692950375090,-5916148583155896695,-8488144396298665448,-3689695075517026779>()) {
                        case -1676226016:
                           var5 = b(var0, var1);
                           switch ((int)com.yiyiaddon.m.b.a<"sg6xb59aws63s","IP+AFU7nQVlWleZScEBR2j4ExF3A5ydgVmE+2pz0CXI=",8414179526534658002,568648309522920706,6719877983146285087,1788270622085729310>()) {
                              case -1219206029:
                                 break label42;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  if (var5 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1wqi7z4iwlef8","g/efMgbNg7zQPquztc6zyuwRUqcwY7HhrBRRnEk5YP8=",-7859135301843574543,-5644858194665039675,-4831592140330604625,-6025740929069417197>()) {
                        case -1693014391:
                           switch ((int)com.yiyiaddon.m.b.a<"s1zn4e0l1elk8p","ByAmHzCBDT67X9ekD3gGww/ejiVUdNVQvJPnQ0Zke9Y=",4590413677570691896,-6875967492887048914,2592323296960111683,-4109093223010270565>()) {
                              case 213763616:
                                 return var5;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s21wrspnq1pw9","bKIzNfx+B0f87gSsvjZEia2Dk6fDuMz1jMxSYg92KoA=",17825580415150446,-8718997447872053101,-4690741799373027019,-686541633382556707>()) {
                        case 1847017936:
                           return var4;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3g78247sx4w6w","OEck4zw9z5v3Q3o3IwLXjHCQA8VTVVDCxZu8QGzgYf0=",-6690303270710915692,-6957319216265256413,3362070349933651478,6925267119794580373>()) {
                     case 1415931005:
                        return com.yiyiaddon.e.n.j.b.a.b();
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return com.yiyiaddon.e.n.j.b.a.b();
      }
   }

   private static com.yiyiaddon.e.n.j.b.a b(BlockPos var0, r var1) {
      Iterator var2 = g.b(var0).iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s397qidr0q5dhd","vpubY22Ngb0NeZLPv7xg+JlyJqKF3FrVMedPS6sPHdw=",7629277384699129083,8096040451115716010,-2004385638071756687,-3115852834123774387>()) {
         case 2042658243:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s2fdxiw6q0wbug","m/4bXPF6MlJcAm/gtjCjV5V1EB33z08Uz3/NY91Pg1I=",6877052474308519975,3800441720890239811,-4485093024471536954,-8536823239221162401>()) {
                  case 1735046278:
                     String var3 = (String)var2.next();
                     com.yiyiaddon.e.n.j.b.a var4 = a(var3, null, var3, var1);
                     if (var4.a() != com.yiyiaddon.e.n.j.d.UNKNOWN) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3h15nox085oa2","8JQBscq8Y/1qET2fOy9AOQ2aCDh1+f/+O02yVlU6fYc=",-8203596178109720325,2123715575926257023,-1935815019099767581,2143875915955191749>()) {
                           case -1814291054:
                              return var4;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s23o6vzphdgh6w","dOg5mcjgYujKiXYMSKEZq00QfeVfeHunNCY+FwDopNc=",5972545221973926879,3616224303275543703,-7320058893227334983,-1161767650918534775>()) {
                        case 504074958:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return null;
         default:
            throw null;
      }
   }

   private static com.yiyiaddon.e.n.j.d a(com.yiyiaddon.e.n.j.c.e var0) {
      switch (var0.b()) {
         case MATURE:
            com.yiyiaddon.e.n.j.d var5 = com.yiyiaddon.e.n.j.d.MATURE;
            switch ((int)com.yiyiaddon.m.b.a<"sk0dwali7apd8","pnb0R8ZCwMxqLWImGqCDx/AY0xSssFsVLUXw5xQ6AFg=",8203752185851127059,-1615074083768302771,-1250329512398313960,-8350719698157142433>()) {
               case 295822804:
                  return var5;
               default:
                  throw null;
            }
         case SPECIAL:
            com.yiyiaddon.e.n.j.d var4 = com.yiyiaddon.e.n.j.d.SPECIAL;
            switch ((int)com.yiyiaddon.m.b.a<"s2sgzpfcuq2m0d","KJObZCSOTjMmxa4jAhiiRLNlLuhKfS4UeqG6aGQuIFA=",6277287738152088698,-2707573679419932562,-3746953513997803123,2296885442669738751>()) {
               case 576516019:
                  return var4;
               default:
                  throw null;
            }
         case DEAD:
            com.yiyiaddon.e.n.j.d var3 = com.yiyiaddon.e.n.j.d.DEAD;
            switch ((int)com.yiyiaddon.m.b.a<"s2w7rjbbf0552l","hqAf0LA7tfRHMRq99XyZ5ANlEpTmVIhA0axNn+euWVw=",5633899344166214039,2258822880828673424,-1317518642007067987,2004456773598189091>()) {
               case -121597439:
                  return var3;
               default:
                  throw null;
            }
         case GROWING:
         case REGROWING:
            com.yiyiaddon.e.n.j.d var2 = com.yiyiaddon.e.n.j.d.GROWING;
            switch ((int)com.yiyiaddon.m.b.a<"snb6zji60gh8l","aDYiT3NjNQByVYNeACdjJGMcM0VRdZmhL105ZAp+C4w=",3209335474945838417,-3057419378552288676,3717843756554617427,7327900131796820038>()) {
               case -1347301926:
                  return var2;
               default:
                  throw null;
            }
         case UNKNOWN:
            if (var0.do()) {
               switch ((int)com.yiyiaddon.m.b.a<"skcjil1ttz96q","6G+z+5hWe/hYRUHjwAQ66YgZAhUeYeX7ZiYZakVhv3A=",1022454376458330076,-6893406095100656963,-3123148238614908030,-5383371950919547071>()) {
                  case 571320194:
                     com.yiyiaddon.e.n.j.d var10000 = com.yiyiaddon.e.n.j.d.GROWING;
                     switch ((int)com.yiyiaddon.m.b.a<"s3s42p6nlfty3u","bUVM7pulB6aCMNgvOkFsrgWoXfSsS8b1FloWbPte/L0=",-6550648301578704719,2638453531460633144,-4816486579396974224,-2470274504878830702>()) {
                        case 234497229:
                           return var10000;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               com.yiyiaddon.e.n.j.d var1 = com.yiyiaddon.e.n.j.d.UNKNOWN;
               switch ((int)com.yiyiaddon.m.b.a<"sv0ctn4moyzcq","nQn57iKxCLYwy/9kiQOQYbmKt8tKGag0GexmxbkYrVw=",-5075923135878802354,5767969278498578283,-761129366315688437,8086344473075899361>()) {
                  case -1604910607:
                     return var1;
                  default:
                     throw null;
               }
            }
         default:
            throw new MatchException(null, null);
      }
   }

   private static com.yiyiaddon.e.n.j.c.c a(r var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1u9yzyulnav7y","QsFcuHQ7BnfGPeRH6fVV+Bo51nK4S/U75MTFOyB4/QM=",-2503813760546286593,1478373844898277842,5743572681227135947,3952963253941129051>()) {
            case -1743832984:
               return null;
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.e.n.j.b.c var1 = a;
         if (var1 != null) {
            label23:
            switch ((int)com.yiyiaddon.m.b.a<"s2s7inhzftg3iq","9tIeu5CZ8uF1Rf8iVjFieImFrQIBEYiKLLDnz9ivscw=",4360036414266686741,-7137801976187024137,-115489320811230252,1792520058086018389>()) {
               case -2032949328:
                  if (var1.a() == var0) {
                     return var1.b();
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"ssprunc51dbxm","mYPORPrEq6DTPY7wFEZJmho1FJj9U+cxZmUnXCXs/WA=",-1552415084814755915,726125324893266003,-1422408813384242519,7825425660530361162>()) {
                     case 448890196:
                        break label23;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         var1 = new com.yiyiaddon.e.n.j.b.c(var0, new com.yiyiaddon.e.n.j.b.d(var0));
         a = var1;
         switch ((int)com.yiyiaddon.m.b.a<"s2yru7v8nhhm5q","jUz/8t3mLAWKbr20bprSBuvfudcLM206+QkxPPS6Iuk=",-5189093405041531549,2838374068807761148,-5594616673223935008,1096439844137170653>()) {
            case 1201435941:
               return var1.b();
            default:
               throw null;
         }
      }
   }

   public static f a(BlockState var0) {
      return a(var0).a();
   }

   public static com.yiyiaddon.e.n.j.b.b a(BlockState var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3643v10ek018y","Fj7fW2zAp4o/I5KjrdeDiA+5TMAuqHefg4uikV8buM8=",7280373109990862567,-7471540804802680811,1237758241201085407,1388444022149562530>()) {
            case -325793774:
               return com.yiyiaddon.e.n.j.b.b.a();
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.g.d.a var1 = com.yiyiaddon.i.e.a.b(var0);
         String var2 = var1.dv();
         if (var2 == null) {
            label74:
            switch ((int)com.yiyiaddon.m.b.a<"shgf2fplhr2xj","8ZewNjl65729o2i0cnSODnA1YQVcEMdb1umMt6ZzRlA=",-5410733359716878294,7640032755451253819,-6157620425197047373,1291272248979908046>()) {
               case 1227729125:
                  String var10000;
                  if (var1.a() == null) {
                     label69:
                     switch ((int)com.yiyiaddon.m.b.a<"s3tnsxi07vj4jm","LU0a7bUHEqR6+RPcEf4G/Ypk76v5jEtDPO5GhycFvZg=",135199195337453452,-8898304843506938639,-4737874578448045468,-4685879969764848167>()) {
                        case -1816561117:
                           var10000 = (String)com.yiyiaddon.m.b.a<"s6db3g4d96tz7","TieeBUbP+Ra+NqYponq0r85+Ytxt4b2MM92sjw==",-2264228723293619301,92294806668579293,-1088846549291643382,-974414940230683152>();
                           switch ((int)com.yiyiaddon.m.b.a<"s3ghpohvhd9iol","3bh0nHn+MMzWpqjiCgrxCGIgJdSz6lwLZl8UqVJEkZE=",5038218000276915727,1542879132055009581,-2473710568460641749,7004309387022615997>()) {
                              case -828977993:
                                 break label69;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var10000 = var1.a();
                     switch ((int)com.yiyiaddon.m.b.a<"s2osolrcefavfi","SgkwXDe0l7XtH9AzIcs//6YI61wKDLeNMVBCHeRAoFs=",5228301255197075941,141115116739022356,9070202986358530252,-8165981286965264974>()) {
                        case -1369894669:
                           break;
                        default:
                           throw null;
                     }
                  }

                  var2 = var10000;
                  switch ((int)com.yiyiaddon.m.b.a<"s1ub7ojjmi6zzh","M+wSeed57PqfnD4qSc1vht1znagE7uS0QKDIHrLPSy4=",-838589486807743488,1616411944206391559,8802461107116593291,7801041301055121827>()) {
                     case 280139859:
                        break label74;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         f var4;
         label91: {
            String var3 = var2.toLowerCase(Locale.ROOT);
            if (!var3.contains(
               (String)com.yiyiaddon.m.b.a<"s2jx9fz464qgp9","nG/XNXykgwFohJAABGg59FOSfqQJTy6lR1SEJKGtGvtHlw==",-8395276319754480641,6920677787219188815,7778808147701489171,-7091637660611068802>()
            )) {
               label63:
               switch ((int)com.yiyiaddon.m.b.a<"sswyj2t97n907","n+UAIkspP2rtX5f3qvzJQcn3k1SS0bdJ5t90BNBWCP4=",424621672589199990,9014214715905607851,4947890706712127888,-366590088122541589>()) {
                  case -2162870:
                     if (!var3.contains(
                        (String)com.yiyiaddon.m.b.a<"s2hvv9z6ehbfq","MbasfXjQ29cxojILqkC97tnt8rldrl8mZcJlmevytQQ=",6088286544541688499,-6792320845027081293,-3982316147459655823,6086883566454327028>()
                     )) {
                        if (!var3.contains(
                           (String)com.yiyiaddon.m.b.a<"s3owxu8ig43dzg","0o7FppcbOtX55XAr+iZuuYnJ0W3mK/Z2vEcWsdqxO6LSqw==",7497638786229495650,-2843024528370463276,-6973036607221570533,6350671776110233174>()
                        )) {
                           switch ((int)com.yiyiaddon.m.b.a<"s36js8f1a4n3mr","GO1DHfEL/145qDVJw1O0nm1zuwtgoRc7vJjjQHNy86g=",-7952449136920574237,-7947253001011401564,3743444218616638525,5798722627630721911>()) {
                              case -1655353726:
                                 if (!var3.contains(
                                    (String)com.yiyiaddon.m.b.a<"s14kzabep0b18q","sgyg9H6TiZajOuUmP7N6RwraD/BZTTLd1t2KL/V10JI=",7895327267753083668,8124884304185022048,-7548892911887962692,-2852851545221158731>()
                                 )) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1omy0xqrp1ozv","1eKNGDgJYnh5YUhOeGrbTxNSw630orDBMUFqYsT+yfo=",-2701887256810199391,-3397812397777044443,7975045471441051741,503206166462032452>()) {
                                       case 558912533:
                                          if (!var3.contains(
                                             (String)com.yiyiaddon.m.b.a<"s1qxwcjx6bg1sk","A/Te37QcBrmVFjtx+K90OOjQ7Gol0s5pwLKilsipYdMemQ==",-8936921007228745000,-8310672321433361994,3411748112583899488,-1977276956686607523>()
                                          )) {
                                             label53:
                                             switch ((int)com.yiyiaddon.m.b.a<"s2blwy92sorkwo","tZuEfUwsVYX1PFDRkAOjsj2k/V+qiXHEUcjfVHWTmvc=",5192052521753967554,1526166546228434231,-7105803386405130331,5918213565766242108>()) {
                                                case 623448471:
                                                   if (!var3.contains(
                                                      (String)com.yiyiaddon.m.b.a<"s1axksjol3q286","GLqKUCP7OnfBQYrLFrDLEizkoVGCJw6RN1Xx9XRl",-6571550808753727209,3708315636409571721,-3280784183161005613,-1733905175185889430>()
                                                   )) {
                                                      var4 = f.UNKNOWN;
                                                      switch ((int)com.yiyiaddon.m.b.a<"szilgk3uiuss2","A017kM5QuH5/J8uYko8LJWxP1985JGXuqcjtWbH4PFg=",7621052373397970997,-9176704456781023353,-7351679873901022627,-3624499399788444304>()) {
                                                         case -1614352919:
                                                            break label91;
                                                         default:
                                                            throw null;
                                                      }
                                                   }

                                                   switch ((int)com.yiyiaddon.m.b.a<"s1pfjfds00pf3b","5GmPdpzR5D5oekd4YOo38apZSmXftrayyXa+eRbJMWY=",7020808246905497119,-6515440813214549076,1248981729900533339,-5591390851538774772>()) {
                                                      case -1095676772:
                                                         break label53;
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

                        var4 = f.DRY;
                        switch ((int)com.yiyiaddon.m.b.a<"s1gsjuodpjvfd9","E6Sx1vKStmiB0ThK2NVl+Rcnmut1Cb7OQmclas4L6jA=",-8102270589259184495,6936687237615478419,2119004887355524875,-8626712241305429349>()) {
                           case 1991457450:
                              break label91;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s1t6r3q3dv1ct1","uVQW4dZ0UIEfrO4C43AuB8zijyMCcNDNnUHbrfao4KY=",-3857551847649465886,-5963696175238322930,-4653824797585789385,-2309924904325939427>()) {
                        case -681368485:
                           break label63;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            var4 = f.WET;
            switch ((int)com.yiyiaddon.m.b.a<"s29nz2x51tpwsf","wQGt4T3kNovKLS5UfKnCvDpkoikugWS4yXFY1NAGEjY=",-806959678890924362,6375959384154466388,3197099353478194399,-8004574875902922398>()) {
               case 779339518:
                  break;
               default:
                  throw null;
            }
         }

         String var5 = aO(var2);
         return new com.yiyiaddon.e.n.j.b.b(var4, var5, var2);
      }
   }

   public static String aO(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s5n88khntpyvu","hNoQJiLjostttuBViifhKO8G71UZY2yn8atTxjiWM/A=",-4939568495264939073,-1746269308351311573,-3186745550262758835,-5619848397260022985>()) {
            case -518204410:
               if (!var0.isBlank()) {
                  String var10000;
                  if (var0.contains(
                     (String)com.yiyiaddon.m.b.a<"s23j35mvquskmq","+X8Ud+rU/GOY8MEP0A0mq2awAKvkyTtey3u3ci33",-94095799689025282,1438434057292264171,8531101255808668129,-7890541163042879883>()
                  )) {
                     label51:
                     switch ((int)com.yiyiaddon.m.b.a<"s1w7qe11j5sok3","JmyroZDuHBgZTz6Trv9VFKFiPBksLt3BYaXHSBr6Hc4=",-2335808383376379078,-656371411078558055,-2799295202456524251,1174098991408134109>()) {
                        case 1998630274:
                           var10000 = var0.substring(var0.indexOf(58) + 1);
                           switch ((int)com.yiyiaddon.m.b.a<"s1p9fzrm4w47bi","D3fQpHp0uoI8H7ZRfoxBE5ePlA8ft78DjdCRvYN0QiA=",14173787841407176,-2921490373714557402,-4838149976655086590,5257432002276395402>()) {
                              case -1869099831:
                                 break label51;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var10000 = var0;
                     switch ((int)com.yiyiaddon.m.b.a<"s30as5x2qv5z3d","yyXtUsSWa/j3vxq5O0FKdzKSR2rej93AexHnf+Pnt4E=",3051018664118564587,-70960648314397042,3345722600236076985,-5168494772883017386>()) {
                        case -567584427:
                           break;
                        default:
                           throw null;
                     }
                  }

                  String var1 = var10000;
                  int var2 = var1.lastIndexOf(47);
                  if (var2 >= 0) {
                     label46:
                     switch ((int)com.yiyiaddon.m.b.a<"s3twtxw7fepuri","48J34tWDBAOHBeg8vrHSOXviUwP0AjKDS+l9zpNk14w=",-445225836265430889,2125941317671958479,-938127951481304738,-6357711958170672698>()) {
                        case -2029088118:
                           var1 = var1.substring(var2 + 1);
                           switch ((int)com.yiyiaddon.m.b.a<"s2n1aqbj9y1esc","Nn4bnLkhF2GFaoxZYXA9i0wsVlByogES+uAPH8Ilow4=",-3220909795374955038,4322939859270535542,3564456524960085643,9014931850234611764>()) {
                              case -274561075:
                                 break label46;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  String var3 = var1.toLowerCase(Locale.ROOT);
                  if (!var3.contains(
                     (String)com.yiyiaddon.m.b.a<"s2ce9al4gpnwt8","92y8Uk4dUplJsHfRYhkVGbLEy3wLPvoXWA76p5Rp5P6ulPjsid1d0h5u",-6443803445717965057,3410653670408870197,57454925701320955,-4215045008942979257>()
                  )) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3b8yoryom1us4","wXBK8YrTkkOiq2+17BWeUXGKaPI3y4UCLgcV074UVyc=",-4380947566699540386,2413547855259006697,2285985313202436718,-1967470113920409403>()) {
                        case 146566234:
                           if (!var3.contains(
                              (String)com.yiyiaddon.m.b.a<"s1v2te1lm5jd4x","y8FadYc1zxDLKdwDBigb2FjL0nqKNslNl+/tpZOKmlhce7Bs0XxbMTZX",-1721194533684193634,5515872895208282803,-2754089808036879119,-2684979052286547007>()
                           )) {
                              switch ((int)com.yiyiaddon.m.b.a<"s4ep1702bbys8","XzQiYzlMYMjdafzNFYBSounBHklyjeQCj6fiUhtXe0c=",3361372036649165294,5031628927367713925,6262348477199666914,2392494747479442642>()) {
                                 case 849859276:
                                    if (!var3.contains(
                                       (String)com.yiyiaddon.m.b.a<"s3tt2pxtaoijc6","1UrdHFEgpHNcYeD6WNK7/0H83H3AeHzJTMkxB2CyG3BfYPQj",-6279579050020264639,-1031606119811124950,7764817407028357052,-7863916490973529961>()
                                    )) {
                                       return null;
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s1sc0441r3kopt","N/jrOBzbnsnCt0OXQ5xqury41mWZyHuxwN9LkzcGfjg=",4832378499743563038,5732620809015968271,-1286300148256906604,4447411133291866843>()) {
                                       case -2079195264:
                                          return var1;
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

                  return var1;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2w26uu0db02po","AjjLOvc7CzS//X3TT0fIg3YnsGhZGQonMVSOweLutlI=",-5947398709544383352,-711885451727928047,-2113719850697049526,1550373685623975365>()) {
                     case 1447833343:
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

   public record a(com.yiyiaddon.e.n.j.d a, String tj, String tk, String tl, com.yiyiaddon.e.n.j.c.e a) {
      public static com.yiyiaddon.e.n.j.b.a a() {
         return new com.yiyiaddon.e.n.j.b.a(com.yiyiaddon.e.n.j.d.EMPTY, null, null, null, com.yiyiaddon.e.n.j.c.e.c);
      }

      public static com.yiyiaddon.e.n.j.b.a b() {
         return new com.yiyiaddon.e.n.j.b.a(com.yiyiaddon.e.n.j.d.UNKNOWN, null, null, null, com.yiyiaddon.e.n.j.c.e.c);
      }

      public com.yiyiaddon.e.n.j.c.f a() {
         return this.a.b();
      }

      public String dk() {
         return this.tj;
      }

      public String dl() {
         return this.tk;
      }

      public String dT() {
         return this.tl;
      }
   }

   public record b(f a, String tm, String tn) {
      public static com.yiyiaddon.e.n.j.b.b a() {
         return new com.yiyiaddon.e.n.j.b.b(f.UNKNOWN, null, null);
      }

      public String dU() {
         return this.tm;
      }

      public String dT() {
         return this.tn;
      }
   }

   private record c(r a, com.yiyiaddon.e.n.j.c.c a) {
      public com.yiyiaddon.e.n.j.c.c b() {
         return this.a;
      }
   }

   private static final class d implements com.yiyiaddon.e.n.j.c.c {
      private final r b;

      private d(r var1) {
         this.b = var1;
      }

      @Override
      public boolean U(String var1) {
         if (this.b.aA(var1) == null) {
            label22:
            switch ((int)com.yiyiaddon.m.b.a<"s1sp05cxgfz04","58eg8tN3w+DNPBt4pzXrdroPnA+K23Mw+xDa2n/9VCI=",9059796569417468808,-3464432934394838357,7694338710001205382,-6062602319301888266>()) {
               case -630134240:
                  if (!this.b.ad(var1)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s19mblnywpeowa","NIZDvoi5kr6RFbiVmWDyhe08FrQCuvdML0SLN1aSZCg=",-59329986526738701,8681152235787855765,5266892224233387186,-1530133376927231748>()) {
                        case -80592490:
                           return false;
                        default:
                           throw null;
                     }
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s3tcsp0jwn5yr6","b7xWHkyq8WSh6fHSNvmNrqchn4I4IP+Wc0V7WDGdJPY=",-4993635155291137962,-1568436683182277629,6962397703483971035,3988097504798537638>()) {
                     case -561214117:
                        break label22;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         switch ((int)com.yiyiaddon.m.b.a<"sn0j8oi12jdhu","2qmBu/N1b/IQGcexuOTZs0CRaSL9+QTpdjF1l3DfKNc=",703797253714884232,4815380908797034213,-7538144419712618398,3267780789215723385>()) {
            case -584649127:
               return true;
            default:
               throw null;
         }
      }

      @Override
      public String aw(String var1) {
         return null;
      }

      @Override
      public String aA(String var1) {
         return this.b.aA(var1);
      }

      @Override
      public com.yiyiaddon.e.n.i.c a(String var1) {
         if (this.b.aA(var1) == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s3eg0v0y0piiam","g1c8PYeFkxwlUcgNy8ftBCdhl8FEDv6TgO+BWIC7X1Q=",-8596098058886670631,7806600354404348764,5500416465964053052,2575393789745936762>()) {
               case -1967285167:
                  switch ((int)com.yiyiaddon.m.b.a<"sm1e7oc2dak6v","TQy1mrL7oaXlRhg/m4jbjC/+gs4Qx1g4atblVB8hm0s=",603077969990967243,-5118464309380661855,2955928492811193777,-2063936454861402611>()) {
                     case 848967094:
                        return null;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            com.yiyiaddon.e.n.i.c var10000 = this.b.b(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s3gqp9ekb07es","Y+mHjDc3nxZX49F+cSCVSHddcterZSow8IUsaI7xkPs=",7003030448114043614,1613110276869566291,-7168320632359454522,4675197550691220976>()) {
               case -208358821:
                  return var10000;
               default:
                  throw null;
            }
         }
      }

      @Override
      public com.yiyiaddon.e.n.i.g a(String var1) {
         if (this.b.ad(var1)) {
            switch ((int)com.yiyiaddon.m.b.a<"s3ni1e2pm2wn8","Astc6JufaWIYT9YbN9BM9Pw8fisM+y7n2GGaV88WtLs=",973970008242690720,-8352105373596180412,-6867999029179693549,-1572859010919567011>()) {
               case -412222321:
                  com.yiyiaddon.e.n.i.g var10000 = com.yiyiaddon.e.n.i.g.REGROW;
                  switch ((int)com.yiyiaddon.m.b.a<"s1oaqofswraml9","Oxa6G6GS4FcHhNbVfHL9+RNwYfKW9AOiqHUDEtchGBg=",1039665698601996348,-3516455583672009781,4628141688870764158,8184428808564827136>()) {
                     case -294829682:
                        return var10000;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            com.yiyiaddon.e.n.i.g var2 = com.yiyiaddon.e.n.i.g.UNKNOWN;
            switch ((int)com.yiyiaddon.m.b.a<"st7iwcu8o9vvw","kiEklC/ejf1F4YBsV7kz5HbKEvTYmqfuaaYXTqVC7cQ=",-2659842834726461555,9031582566262532917,-7528133771648956144,-9060097013068074031>()) {
               case -1375551707:
                  return var2;
               default:
                  throw null;
            }
         }
      }

      @Override
      public String aB(String var1) {
         return null;
      }

      @Override
      public List<String> d(String var1) {
         return List.of();
      }

      @Override
      public com.yiyiaddon.e.n.j.c.b a(String var1, String var2) {
         return null;
      }
   }
}
