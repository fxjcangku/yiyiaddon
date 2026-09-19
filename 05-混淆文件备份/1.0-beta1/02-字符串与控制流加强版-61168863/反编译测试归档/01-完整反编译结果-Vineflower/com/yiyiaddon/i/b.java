package com.yiyiaddon.i;

import java.util.Optional;
import java.util.UUID;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.User;

public final class b {
   private static final String DW = (String)com.yiyiaddon.m.b.a<"s1l29ktmgnmg9c","H1Okl0ebTLk1BPbs0BaD2rbofqvgTHLAiLgfdpWMdmirhBUG3ocBxDJnvFf9yA==",2798313674122778976,6236575283237413340,2204942814951193526,4742494625109501387>();
   private static final String DX = (String)com.yiyiaddon.m.b.a<"sftk37mra44dn","as0dOioFHoUdMAl/wqpeL61S8Q5PFxnGXq9dEOHgt/U8jH2CI8tkarkIHhY5BJ8kEbHGQg==",-4767176042452085285,6148990290344206659,-2167529824429595220,3015928395454631123>();

   private b() {
   }

   public static UUID c() {
      User var0 = Minecraft.getInstance().getUser();
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3dyqcy0t81jaq","wiGMZzCb1BjbWpOhL9AgDp7+tYY8ksuFPHqIrbwehQQ=",1253866048587073911,-5424431772769915580,8495283602267193636,3915195753842997216>()) {
            case -437396193:
               switch ((int)com.yiyiaddon.m.b.a<"s2cze1fovtlnbr","paDdlwXW+dcGfKLcr0KRC941Kdr526YLmZn+/FyIcIM=",-1880966620922032211,-4240499578773262258,8690638864387297171,-5989939910895212534>()) {
                  case 973503464:
                     return null;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         UUID var10000 = var0.getProfileId();
         switch ((int)com.yiyiaddon.m.b.a<"s3olz1emhmqw3y","Jkz2bvOCjZs991ahQJ7eLy/0+NboE/jMEicOmDRYjX8=",-1298156514247249976,4667607026233808865,4294216435600240870,8975380455738481481>()) {
            case 140572377:
               return var10000;
            default:
               throw null;
         }
      }
   }

   public static String gg() {
      UUID var0 = c();
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s32ebj5uek6vip","y3mTfyRFTHPiJGviGWlarNTKwCiT7NVWLfA+GEs4xhA=",-8518885750925313313,-7563788237677226278,-9175837115573962434,6633308840980064649>()) {
            case -1856098351:
               switch ((int)com.yiyiaddon.m.b.a<"s2hy9pkha6j17h","JGIEwQEioyMQCTnTKJqEE0XzITW/WiUoNS09K3vlzeQ=",4905071481605583013,-7369775948472866540,4870385997243531554,-8275885995286020562>()) {
                  case -1441669645:
                     return null;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var10000 = var0.toString();
         switch ((int)com.yiyiaddon.m.b.a<"st131lkpvaib0","sYqwF/0fqhB+2JyP473UQL31SsIS6zWZwTSxSZ4q+4w=",2114872644306205445,-3022481237279942605,-149936960993604045,4840612795427357762>()) {
            case 606690399:
               return var10000;
            default:
               throw null;
         }
      }
   }

   public static String a() {
      User var0 = Minecraft.getInstance().getUser();
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sm9mo6b3f2ka7","a33Jbzb1lIWen92P99fc45laq8Jx+8FxScZUns+HuvM=",6675387920988243044,2930495826273247968,6473581101320209546,-7106918845726514761>()) {
            case 880981108:
               switch ((int)com.yiyiaddon.m.b.a<"s1dvapez5tn6jv","aV1CN1gsBW3TzMHAmFXp9+Wkf4N3aRd5rOWxS/MwHR4=",-3519333667192768763,2954750425327339018,-1826391663972101548,8623716986184570922>()) {
                  case -804404148:
                     return null;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var10000 = var0.getName();
         switch ((int)com.yiyiaddon.m.b.a<"s24nuy3g888lkv","fnE9Q6jAsSih7NMD94Jr7Z3vG3PwE8g67l8/wOXADqI=",7282137053268620852,4822875456022744199,5744901622085778118,-8255146262794586920>()) {
            case 1120262704:
               return var10000;
            default:
               throw null;
         }
      }
   }

   public static String gh() {
      try {
         User var0 = Minecraft.getInstance().getUser();
         if (var0 == null) {
            return null;
         }

         Optional var1 = var0.getXuid();
         if (var1.isEmpty()) {
            return null;
         }

         String var2 = ((String)var1.get()).trim();
         return var2.matches(
               (String)com.yiyiaddon.m.b.a<"s2b16lfmclzs8p","P1hTz18VjhUjdHoKqZUBU8aaMu5glqhpBtslweaoCEG47BrcZxmFveL7Cf8=",-3284043126690742844,6292341830448777750,-7683957345596764567,1903676167040264212>()
            )
            ? var2
            : null;
      } catch (Exception var3) {
         return null;
      }
   }

   public static boolean eX() {
      if (gh() != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2rm902cs6s018","5GnJmH1T0eJhUQb05iDBVoHpY3K8ZPFGKxiMjVfaBwc=",838699771578571681,7159489872009978863,651260171736890371,-9144816278267562502>()) {
            case -1304791478:
               return true;
            default:
               throw null;
         }
      } else {
         return FabricLoader.getInstance()
            .isModLoaded(
               (String)com.yiyiaddon.m.b.a<"sftk37mra44dn","as0dOioFHoUdMAl/wqpeL61S8Q5PFxnGXq9dEOHgt/U8jH2CI8tkarkIHhY5BJ8kEbHGQg==",-4767176042452085285,6148990290344206659,-2167529824429595220,3015928395454631123>()
            );
      }
   }

   public static String x() {
      return FabricLoader.getInstance()
         .getModContainer(
            (String)com.yiyiaddon.m.b.a<"s1l29ktmgnmg9c","H1Okl0ebTLk1BPbs0BaD2rbofqvgTHLAiLgfdpWMdmirhBUG3ocBxDJnvFf9yA==",2798313674122778976,6236575283237413340,2204942814951193526,4742494625109501387>()
         )
         .map(var0 -> var0.getMetadata().getVersion().getFriendlyString())
         .orElse(
            (String)com.yiyiaddon.m.b.a<"smdewn41xi79","fQYUDHxhW55r74oeKClh3nJxnYE9HKBvn9fQpvuJ07P3I1RVXAyjgmQL",-3820605746382661403,-3937347150185000956,-7390571924312073019,6608815370080261930>()
         );
   }

   public static String bc() {
      try {
         return Minecraft.getInstance().getVersionType();
      } catch (Exception var1) {
         return (String)com.yiyiaddon.m.b.a<"smdewn41xi79","fQYUDHxhW55r74oeKClh3nJxnYE9HKBvn9fQpvuJ07P3I1RVXAyjgmQL",-3820605746382661403,-3937347150185000956,-7390571924312073019,6608815370080261930>();
      }
   }

   public static boolean aH(String var0) {
      if (var0 != null) {
         label22:
         switch ((int)com.yiyiaddon.m.b.a<"s3c0jrao1fmggc","Rm+ZN8jy10pqjtIesLe52rpjO7pKkogMfVCqiiLCzA0=",1211521255443292831,6239949433267991496,-1683186137390703464,-2575594538167864068>()) {
            case -1293226516:
               if (!var0.matches(
                  (String)com.yiyiaddon.m.b.a<"sxp0ko2qils7m","YhLYIemfcXH2LJw5t3TM80Q1sgKBjIbbUvaHwFKxBfxsgRDnDCklQPHrzS7pjStHigo=",2022381839582853516,-4570065898097256595,-7484942679406561688,6052629692856634409>()
               )) {
                  switch ((int)com.yiyiaddon.m.b.a<"s334ugc23gm6io","l5Pjz4ftr+iqNDKunW2EDfqI83PT6XK/A9kAlIvon2I=",-4775906807351330620,-1756677248531285464,5152222470289350065,893038968049329123>()) {
                     case -1681874513:
                        return false;
                     default:
                        throw null;
                  }
               }

               switch ((int)com.yiyiaddon.m.b.a<"sj92zf9yt3w16","50uPVplgtnv/+xPjcADzZ3CVTOL5uLsFDadQxGkzYEg=",343954921818291076,-3800880433746681885,-5926412138210162227,-3521521898487530552>()) {
                  case 1574458148:
                     break label22;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      switch ((int)com.yiyiaddon.m.b.a<"s2onhhedr7mhlw","G05TYS2v7keuWaj6y7keZ2i4Q6N0P/xAUeNRYWAsCNs=",-3394719938842974979,393811532556330010,-8078156000833420307,4355339684946305437>()) {
         case 1915206216:
            return true;
         default:
            throw null;
      }
   }
}
