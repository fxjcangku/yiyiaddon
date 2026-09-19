package com.yiyiaddon.i.g;

import java.nio.file.Path;
import java.util.Locale;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.world.level.storage.LevelResource;

public final class c {
   private static final String Ek = (String)com.yiyiaddon.m.b.a<"s3rrglws8mb2rn","Hec+YT9fxahnEvmfg7T+0Si8FSrhzl0wDZMNOhdA1WbIxdnLmLfkcPSzPSJptajOLTO3TFkq",1342963441067805869,3366095215434112908,-54957389140698988,-1292837444947563209>();

   private c() {
   }

   public static String fG() {
      Minecraft var0 = Minecraft.getInstance();
      ServerData var1 = var0.getCurrentServer();
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3hot2iaip0sfo","GX3wd4EBCZcvT2H7aRJr7htjONWtcpYvM3Px3MPw0Ig=",7553480407262790666,231971239490684937,-7651759442819569139,8505641172179839978>()) {
            case -1315227280:
               if (var1.ip != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2l937mys78gm2","XkmjY+4j93+2HAeL8KrupBAZqrubzjfMRZpVGPXbVVw=",-3976845852404726986,4769069814397905461,6938157336784144962,3637656876668317873>()) {
                     case 756634560:
                        if (!var1.ip.isBlank()) {
                           switch ((int)com.yiyiaddon.m.b.a<"sn5wqwa60wyf","aNCrJvCnxuLhPu22swsV6IBQ8rWAKokMY6nq33yyByM=",-1484958748884850602,-3626031465366025972,2952232262021987075,3697832967048040157>()) {
                              case 561778148:
                                 String var2 = bX(var1.ip);
                                 if (var2 != null) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2ndu2gz0pzxke","EBtSYY80XIN1eLp/BeZXXVD/CEAvw7TjpLyHKaXydjI=",7292483130820166371,5837187338231731523,8368647271633294160,-5322150261261165759>()) {
                                       case 657992363:
                                          return var2;
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
               break;
            default:
               throw null;
         }
      }

      String var3 = V();
      if (var3 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ctdgv5vqge1s","z+93GCfnKrPKeQcBr8KetQCsuxhdFMfnKxfI+ZLMuHc=",8337759142675903673,-1128357786902615986,3696016050975111699,-8564632625188627800>()) {
            case 1313951084:
               String var10000 = (String)com.yiyiaddon.m.b.a<"s27uich21tw6jd","4YFuP0m5fSzoVMV4p8HIwlN5Y7QPIPOMG9ERChGU/u8MxAkySECFpUTT",1166675437225045741,-5418268029321418225,3283397973669693012,3329194155077184976>();
               switch ((int)com.yiyiaddon.m.b.a<"s7zbo64re4gwq","vTUwT3I2piLoenY6t+YDzjWKXJFvZiJu1ZKEOtAnI+o=",6731163771248158310,3717240688832971175,6914947547437259649,-6723076138003607964>()) {
                  case -269587047:
                     return var10000 + "";
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s27nrqp8lg0uxc","Lqrb6Nw3XYTpgXEb+2aZUoXf77DRCmiDIwyqhZsbvXg=",3635493127367528926,7886832416528598311,-7608491589415352892,-8356336194934162322>()) {
            case 634775817:
               return var3 + "";
            default:
               throw null;
         }
      }
   }

   public static boolean cH() {
      return Minecraft.getInstance().hasSingleplayerServer();
   }

   public static String V() {
      try {
         IntegratedServer var0 = Minecraft.getInstance().getSingleplayerServer();
         if (var0 == null) {
            return null;
         }

         Path var1 = var0.getWorldPath(LevelResource.LEVEL_DATA_FILE);
         Path var2 = var1.getParent();
         if (var2 == null) {
            return null;
         }

         String var3 = var2.getFileName().toString();
         return var3.isBlank() ? null : var3;
      } catch (Exception var4) {
         return null;
      }
   }

   public static String bW(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3dzpuckvhxtis","d6lNNGHY7XBWMf2GfFD2jASYNj+NQcySsuerDgSQ2iY=",-3229432525235836920,-4585763677762725259,6789141748220029143,3848877978505454504>()) {
            case 1021996251:
               if (!var0.isBlank()) {
                  boolean var1 = var0.startsWith(
                     (String)com.yiyiaddon.m.b.a<"s3rrglws8mb2rn","Hec+YT9fxahnEvmfg7T+0Si8FSrhzl0wDZMNOhdA1WbIxdnLmLfkcPSzPSJptajOLTO3TFkq",1342963441067805869,3366095215434112908,-54957389140698988,-1292837444947563209>()
                  );
                  String var10000;
                  if (var1) {
                     label50:
                     switch ((int)com.yiyiaddon.m.b.a<"s3uuzp8gxg0m0p","LUhmVNHA/9xjXXTd/PcQdk+v5pB5EpI4wviCXbkk61s=",2459265181251941829,-5384958082499953554,-5113003154236427123,-546544799791481169>()) {
                        case 1264108037:
                           var10000 = var0.substring(
                              (String)com.yiyiaddon.m.b.a<"s3rrglws8mb2rn","Hec+YT9fxahnEvmfg7T+0Si8FSrhzl0wDZMNOhdA1WbIxdnLmLfkcPSzPSJptajOLTO3TFkq",1342963441067805869,3366095215434112908,-54957389140698988,-1292837444947563209>()
                                 .length()
                           );
                           switch ((int)com.yiyiaddon.m.b.a<"shpgovkamex1e","IG/FVabe0wDnzzPH5pOnK8DelAP7Xkby6jO+SZJK7xs=",8734562614004124947,-3207840556509876963,-8952069031666405955,6678002365298926317>()) {
                              case 1904130249:
                                 break label50;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var10000 = var0;
                     switch ((int)com.yiyiaddon.m.b.a<"sc57qyqccbfcu","kOhTlQ6OEGcskV3H0uY5mH3X0aa80hJBjeBWI9Ijf1Y=",2424598520810663929,1279317714876629134,1893546478100602220,-7219740931622215368>()) {
                        case 141052519:
                           break;
                        default:
                           throw null;
                     }
                  }

                  String var2 = var10000;
                  if (var1) {
                     label43:
                     switch ((int)com.yiyiaddon.m.b.a<"s3onvlzfuirl4f","VFzq27K2lsIvXRJphr3LdAiuidSrojQkWgvoh9uSVt4=",-7419171178603626507,-6604275917682152173,-3946806160210313682,1589051849088723573>()) {
                        case -434549913:
                           var10000 = (String)com.yiyiaddon.m.b.a<"s1a8e8b5m0mso1","Vb1Gb7LF1rPPb825tJSLHc2x1pta9tuaBB52HaX6NqxCZeHROpEK9p6HujwdVBEnQrYcrUqM",-2021163847520934263,6728059366722601696,-8211902096397748152,-9134782806574428676>();
                           switch ((int)com.yiyiaddon.m.b.a<"s9bcd6uicyz0u","XlbtYXZF9K8B4udfJbx1GJsGO2/71AcL2HDCOv7jsSM=",-2840810319925784820,-7818972991272531689,-4145400400913575647,7994324410068844528>()) {
                              case 1931707559:
                                 break label43;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var10000 = (String)com.yiyiaddon.m.b.a<"sl62qcl1y8f2i","5831TkDHDWzUo1IjcmqoRWqsiaoKVxCRa2ve9Q6H31tRkzi1KSf9k3MJ",-1374497803872559250,3267494745570818638,1410168928437732863,-6596130402375872475>();
                     switch ((int)com.yiyiaddon.m.b.a<"sad0wambwjod1","e1svHGTZRBhlz/muVJqMBzYdgFH0ISoP2dcr0EnJGK4=",5719113386816475072,-603587579671020934,-8057356470356518024,-2281296803236590470>()) {
                        case 946862302:
                           break;
                        default:
                           throw null;
                     }
                  }

                  String var3 = var10000;
                  String var4 = var2.replaceAll(
                        (String)com.yiyiaddon.m.b.a<"s9avzjq212z81","u4AO2/tMgGsIcTGEDv4iG2GHt8raKlgecTX8Zi23eYHrXaWpyMNVWL3zD58R+F7k/a9Xm8pxo1RtA9nCetjg1zdUEK13Eg==",-3648174254449795772,-6298316881228748801,4524061746273932161,2773401226856587894>(),
                        (String)com.yiyiaddon.m.b.a<"s4805416vp23y","p67ah5fOg84T+KYYq4oJ+TgKAZF2mpr9wsYP2fFD",-7341658221718960751,-220401479782157449,-1611447935161923359,-5845414805294917725>()
                     )
                     .replaceAll(
                        (String)com.yiyiaddon.m.b.a<"s1zqwa2ry33p6t","qu3q7hJV5ygLRYLE5UhHbXeqaKNbUae1jabtHsMbRpA2pw==",3346504452505073744,-387486518193207581,-156327334625533994,-8926081029227904827>(),
                        (String)com.yiyiaddon.m.b.a<"shnvkexrgdcs4","9gA9uuJaNYx7s2LEXwOvBK8DCEz4gv7e43hQtrpF",8236189159194417099,348440309906375208,8231232711206245794,-9203218837632725232>()
                     )
                     .replaceAll(
                        (String)com.yiyiaddon.m.b.a<"s125nmxth40r2r","YrFQYVbn6udseMyBgkXDBU36KohJ5pB9yR5BU+amUJfP76Lbevn0gZ4z/tJJYaWQpcHA72nT",5264267898733147005,5174508544052089688,-4987543362404082435,-1229194026420291608>(),
                        (String)com.yiyiaddon.m.b.a<"s1jrgvkry24web","4NCpXjhAcpRQcE75dX6hNL/qgNujplIYh0yZaw==",1846341778109915032,-1864581797668849859,-2914331762449718422,1480913500043541883>()
                     );
                  if (var4.isBlank()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1koykpn952bbo","OZ/gAwSOEphLJQO1+5muybPchJbwK3ShNywqSBN4cao=",1761672433552175828,5019378646417357047,-5382892228768581516,-3230705711171450012>()) {
                        case 1653316077:
                           String var10001 = (String)com.yiyiaddon.m.b.a<"s27uich21tw6jd","4YFuP0m5fSzoVMV4p8HIwlN5Y7QPIPOMG9ERChGU/u8MxAkySECFpUTT",1166675437225045741,-5418268029321418225,3283397973669693012,3329194155077184976>();
                           switch ((int)com.yiyiaddon.m.b.a<"s396c4wlepgqg0","s5SGhCWsewT2aPDBcgBDstUZ86HD9ZauriS0Q84cdyU=",-2468266625090218959,162371214452768399,-1430558008569403542,3983947670822647352>()) {
                              case -206070636:
                                 return var3 + var10001;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s164bmv26j2wkl","/UA2BqGzCSWZp5lk5DPSNfAAsllCSImJittIDamAlN4=",-8793133924908579438,-8019069476614614235,1756579723939598199,-1979490774794110084>()) {
                        case 1435301895:
                           return var3 + var4;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"sdgtlibtlon0o","ubdXhxoZjPZBwH9ZR5yLzEGLZepURFKT23RV9Tgs9Os=",-8352032071415273224,3756400049842036386,-5527590313437534758,8622802328749801198>()) {
                     case -664006007:
                        return (String)com.yiyiaddon.m.b.a<"s27uich21tw6jd","4YFuP0m5fSzoVMV4p8HIwlN5Y7QPIPOMG9ERChGU/u8MxAkySECFpUTT",1166675437225045741,-5418268029321418225,3283397973669693012,3329194155077184976>();
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return (String)com.yiyiaddon.m.b.a<"s27uich21tw6jd","4YFuP0m5fSzoVMV4p8HIwlN5Y7QPIPOMG9ERChGU/u8MxAkySECFpUTT",1166675437225045741,-5418268029321418225,3283397973669693012,3329194155077184976>();
      }
   }

   public static String gn() {
      return bW(fG());
   }

   public static String go() {
      ServerData var0 = Minecraft.getInstance().getCurrentServer();
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3jxs3p2y4qxlb","7MHGgFRUQ/LWODajYH88NkdZuACQ81kLRL5ndmWfxjY=",-6006366731433592430,3160070201231970670,7117268437792464332,-5456718297100894963>()) {
            case -1560670812:
               if (var0.ip != null) {
                  String var1 = var0.ip
                     .replaceAll(
                        (String)com.yiyiaddon.m.b.a<"s3kx5lxm3f3zes","NlfXoz4tgM7uQBdtxl04zYPHRJowa30SRtFPAAiblspyi/dwJ+EZLM15zXhVDwihZxDshD93mm0I4g==",8805665124557051005,277256974980157488,-5406863762170727873,8705755650121366567>(),
                        (String)com.yiyiaddon.m.b.a<"s4805416vp23y","p67ah5fOg84T+KYYq4oJ+TgKAZF2mpr9wsYP2fFD",-7341658221718960751,-220401479782157449,-1611447935161923359,-5845414805294917725>()
                     );
                  if (var1.isBlank()) {
                     switch ((int)com.yiyiaddon.m.b.a<"scl0kp6eifhjb","ckM/VTgosdXIMybK7HmvILveb64R6JqFDUePY9jMeZM=",-7459984848537382288,-5659799801651006792,-970953438078306850,6027011926613385974>()) {
                        case -22199658:
                           String var10000 = (String)com.yiyiaddon.m.b.a<"s27uich21tw6jd","4YFuP0m5fSzoVMV4p8HIwlN5Y7QPIPOMG9ERChGU/u8MxAkySECFpUTT",1166675437225045741,-5418268029321418225,3283397973669693012,3329194155077184976>();
                           switch ((int)com.yiyiaddon.m.b.a<"s1x2k8k6ofzr0t","3bTP4y0dprCxMTcgs4a7aG1mqpoGbFkr3WDVcn/BAbk=",-4669444229033310386,2551206006562681967,-3115505808402435082,1745128804309360696>()) {
                              case 631184305:
                                 return var10000;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s1x3v150plo715","nniLI8pOHgfWsPDv09IUC3WKXneEyu8hWN2pN7ZBSv4=",5077939417514265036,-1910952616401717556,3925545456610710405,7160245632099402749>()) {
                        case -1854139497:
                           return var1;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s17hdypk2pkb4u","XKKWOOnyZONdfgRG2RM5bAs9zsLNzfMbhAPMKC1uf04=",-3970456188714036756,5700708370375455972,-5673042322639858690,-3905226378237294313>()) {
                     case 1070871567:
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

   public static String bU() {
      Minecraft var0 = Minecraft.getInstance();
      if (var0.level == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1b8d8bz445odg","Kn4z6Sv6vsZUuYtCvV5QoLP1BVA/IcCI5ip6qIiOlCo=",7555998864433035805,5348568083124148103,6216891817219361378,-3390819649401406105>()) {
            case -567908784:
               return (String)com.yiyiaddon.m.b.a<"s1jrgvkry24web","4NCpXjhAcpRQcE75dX6hNL/qgNujplIYh0yZaw==",1846341778109915032,-1864581797668849859,-2914331762449718422,1480913500043541883>();
            default:
               throw null;
         }
      } else {
         return var0.level.dimension().identifier().toString();
      }
   }

   public static String bU(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1uf9at9xqsa5q","SUKv6403EbBHzwloBCe93+fiRYGWh3sKwFTAj3nGatY=",2145362392710480822,-5983623230235421333,-1589668267432269744,4242000719632546470>()) {
            case 858176017:
               if (!var0.isBlank()) {
                  if (var0.contains(
                     (String)com.yiyiaddon.m.b.a<"sgnjel5v308vt","Yy/gA2pFZI/VT9VYAXjN70Jxh4aA0YQB+iIM2abrrnOiWzX6oeUyZXp1NISKdw==",3387083091846987828,-490567074210301921,3134498952045782163,-6591179721054035151>()
                  )) {
                     switch ((int)com.yiyiaddon.m.b.a<"s4xtveqry6mnt","K7PPx4LwcLkb4z/PZWy5Pb1Dl6LXaD6YHqTQ61fbvLo=",-7622500587300350566,-8529683129890783656,4451124646533897189,5664603203486068354>()) {
                        case 890367704:
                           return (String)com.yiyiaddon.m.b.a<"s3l10rixnvqshh","xxU547emDep6MWD20IIYvFYOY0kDpynhZYoUtIUEyiwtAQ==",-7169026618171295984,-1647201539961265495,-517620344155575367,3967990425177210087>();
                        default:
                           throw null;
                     }
                  } else if (var0.contains(
                     (String)com.yiyiaddon.m.b.a<"swusx6l0js971","wttTrHS+CJhVK9cIAouEp0oV6czm/Ap9dvgo8lKbfxiORWiLz/2fc5WGP4+AlLZz",6932138534788181030,4419460467229223737,4242934835677213419,4729554005217282451>()
                  )) {
                     switch ((int)com.yiyiaddon.m.b.a<"s39aaxrg9pw3q8","3T1nCVozqO54mopiNlQTcKohcCFkgJDiYPIuOwoGubg=",-5220873882901083472,-3222867514943183473,5096225462650908275,-7811641032224186658>()) {
                        case 527695075:
                           return (String)com.yiyiaddon.m.b.a<"s1uafkg5mtzguu","r+mLjqILs7yb+vz2HUmcCMdNo+k5Gya8jmls3P4jFTc=",8318170002935208604,8619281587383455908,1882350725523190879,4845338817603591908>();
                        default:
                           throw null;
                     }
                  } else {
                     if (var0.contains(
                        (String)com.yiyiaddon.m.b.a<"s2wmxurg3kvglg","WV8BlT34Zpx8jQxgDvKMWcOzj/q9vcgdJ1mexj/qskwEmKVB0vpo8gId",-6948851669577151361,2210772511870645371,7822659249106247115,7290549070183757150>()
                     )) {
                        switch ((int)com.yiyiaddon.m.b.a<"s36iw4dyjzlg8v","j6i7XDHcRK8IrLhmZKJ7AH+vJWTAQKCw5JBxR31NS4E=",-4985808303356235949,-9090244270328058094,8681416724456968039,3643204527954812443>()) {
                           case -1753977841:
                              return (String)com.yiyiaddon.m.b.a<"s334p2baro9mdt","6dtSdwFwETts3zU+bkQEOfLCXgOb7q3DyOwgQq4oQZo=",6903268473809215656,-3753933873394878696,7586357323134636862,5461548571982225497>();
                           default:
                              throw null;
                        }
                     }

                     return var0;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1tkjxvxcm4pkd","lTsp7Xqp5lfRDyFZmUQ2P9b9VwELqdNKxg+of7lnmFo=",401513886569884580,-7467377939550708596,-2691957099591550586,-7731646055238202043>()) {
                     case 1865801857:
                        return (String)com.yiyiaddon.m.b.a<"s2rrfxf7h9997g","B7EVULijtFiw7BAe6JXSMirvZ/UVMyW7I4SR2WATjls=",2664678235611170613,6706973348283180562,-3271865680402086661,-4949858049656857047>();
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return (String)com.yiyiaddon.m.b.a<"s2rrfxf7h9997g","B7EVULijtFiw7BAe6JXSMirvZ/UVMyW7I4SR2WATjls=",2664678235611170613,6706973348283180562,-3271865680402086661,-4949858049656857047>();
      }
   }

   public static int aA() {
      try {
         return SharedConstants.getCurrentVersion().dataVersion().version();
      } catch (Exception var1) {
         return 0;
      }
   }

   public static String bX(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s9i128dy62zuz","EBdlyNqvL6JJJfnQKlPKahWZ89hqCN3r/t8GoXnQxpc=",6442458822139048862,-120209065693411234,1748276002421292570,4265992314800224778>()) {
            case -124465069:
               if (!var0.isBlank()) {
                  String var1 = var0.trim().toLowerCase(Locale.ROOT);
                  if (var1.startsWith(
                     (String)com.yiyiaddon.m.b.a<"s213cwr51fied","yg3L40srGlhzT7owVMPpFxMsbntyhcuWE6FsY0ym",-7497863923023908363,8321968627271697033,-4269866983423046211,1427315828355673246>()
                  )) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2x49khnc6agco","Yp8GVTvNSdc8vsDr8Dgcbjie9V1StKtrRyHt/uDWy8U=",3035908584173876393,7128450984199259841,6545001594929992921,6236307211995964615>()) {
                        case 765617454:
                           int var2 = var1.indexOf(93);
                           if (var2 > 0) {
                              switch ((int)com.yiyiaddon.m.b.a<"s15oy4dkriik4r","Srv6MLvlbczW1bQNUMkwBE0Amgg7v3P1n59JMLFzj8g=",-7517417004353621624,-1466371579015625868,3328260702652619984,-9171062850409169584>()) {
                                 case -600473930:
                                    String var6 = var1.substring(1, var2);
                                    String var4 = var1.substring(var2 + 1);
                                    if (var4.startsWith(
                                       (String)com.yiyiaddon.m.b.a<"s3msjew7wxsntg","BzBZF9Wk6L+wclGMh5PK+I2+lUaAf0aV2tYleqKj",-3513164164420203479,2231743744056296884,-5501143336729251877,-5927477429869572033>()
                                    )) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s1o1oaob5557bo","8r8v2xIqAFnHI9VkQ+u3JfFNDaQ5yQKMmkDltF0j6zs=",-7200777735332225577,6583012701568171322,3906126332016733599,6571851771197091671>()) {
                                          case -1002034555:
                                             if (var4.length() > 1) {
                                                switch ((int)com.yiyiaddon.m.b.a<"sun5rqc1va7sh","SkIgJVyvYo/WXC5EaVx+y0pfd1g0omlayje6QrGW8sU=",3556387230687103939,6321855978586557127,-8894324601518771134,2280208464933084434>()) {
                                                   case 1267541367:
                                                      return var6 + var4.substring(1);
                                                   default:
                                                      throw null;
                                                }
                                             }
                                             break;
                                          default:
                                             throw null;
                                       }
                                    }

                                    return var6 + "";
                                 default:
                                    throw null;
                              }
                           }
                           break;
                        default:
                           throw null;
                     }
                  }

                  int var5 = var1.lastIndexOf(58);
                  if (var5 > 0) {
                     switch ((int)com.yiyiaddon.m.b.a<"s18ql8cy7nls92","Zw0rs5azxc+Khq6yQmiprM9fX3m/B8IY7eDukDg2t4g=",3694427980106909757,-5890023661824860325,-2717575022995594982,-4383279707728470561>()) {
                        case 1935475321:
                           if (var1.indexOf(58) == var5) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1jg8klw7nknfr","Y7gv/K6/gKj3UpipsYmE5G3vygLNzWRTHkzFiu1KE4M=",1979645992297945445,-6745409293122519680,4209488944039325937,-6238048633874228522>()) {
                                 case 1877139779:
                                    String var3 = var1.substring(var5 + 1);
                                    if (!var3.isEmpty()) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s32gk0p46gryyf","o/MIoU2rJdYjWH29NbtaACd8tdXUTrrNSyjdcAbEq14=",-3942344890624998499,8068525732567919584,-2043504059996830454,-2371862811384274141>()) {
                                          case -772525185:
                                             if (var3.chars().allMatch(Character::isDigit)) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s2svqdagq233ed","6CCSGqbi996AlOEPS4/Y6fN3Z0ZAuO1CKMGOviPdNpA=",-8974741912083429812,2391119019623234790,-2555066069655096740,6169405883594696549>()) {
                                                   case 2027761689:
                                                      return var1;
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
                           break;
                        default:
                           throw null;
                     }
                  }

                  return var1 + "";
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3hmk95rm6zmbj","Qo1XgKIHdRZgRcI/ZnSPAfs1ssj5KOQ37qSVLIswTK0=",-261715134975817486,-5412520384870034183,2372705512685178006,-5061203450464010111>()) {
                     case 1878921619:
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
}
