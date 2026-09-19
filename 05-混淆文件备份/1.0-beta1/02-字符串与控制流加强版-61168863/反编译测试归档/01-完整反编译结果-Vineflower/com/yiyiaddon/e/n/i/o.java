package com.yiyiaddon.e.n.i;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HexFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.server.packs.resources.ResourceManager;

public final class o {
   private static final String[] V = new String[]{
      (String)com.yiyiaddon.m.b.a<"s18195zj8fq1ae","Yf2ERXwBVQaSqw3LvzjW3azumSJKL90EqZZtIk+/kU1KSz+f+mQ=",1236959269594557347,3146743067132243681,1205511860003910598,-8294592959514166228>(),
      (String)com.yiyiaddon.m.b.a<"s3ieqpv048barw","onhWUQxpYb6G8Q7MQVsZpqgWFA15lMcFv04m98cMi8Vyh5xB/rcr/BHyko+9daGj80c=",-6409018672868923332,-2784186661051059668,8098774455224008741,-2938087129246198040>(),
      (String)com.yiyiaddon.m.b.a<"s1548k8z614i8x","e3rZddns2XQXgVjqmyoSVaCLS6hhCo5Yd/KGAfqHCpVmXGEzSV+JfLUP35wRnzb1bpAFXA==",804736334118147264,-9115859455714327708,-6576377154589871470,2873967349320582732>(),
      (String)com.yiyiaddon.m.b.a<"srzr53c7piziq","0O+QnZyIljMEC5CbVPWPFeNF+7nWapyeepsioDkuZVSfHe5tamPkNps9vj/fSIXOowc=",-7110318283156730361,8492963062769999232,-219251470071354527,3795026432210403451>(),
      (String)com.yiyiaddon.m.b.a<"sgzl2y5gziw7c","tdaKpBGFvVCtjlydBxvwvQX5Jv8TUdr107KxPZOhs3T8GQu0FnP9GmTb+4w=",5104469726439553385,8083731013670293727,5844923607763522273,7632996039527511021>(),
      (String)com.yiyiaddon.m.b.a<"s1jg80nn6itg66","pyZ84v0tg6qNwx7fpAsr1ZU8Lh+BlYXQQC7cWwKuVcBBllTE",-7647505032894476166,446280913446456700,-6040343497750296222,7884074978524222507>()
   };
   private static final int my = 20000;
   private static final int mz = 8192;

   private o() {
   }

   public static String dN() {
      ResourceManager var0 = Minecraft.getInstance().getResourceManager();
      if (var0 == null) {
         return null;
      }

      List var1;
      try {
         var1 = var0.listPacks().toList();
      } catch (Exception var11) {
         return null;
      }

      LinkedHashMap var2 = new LinkedHashMap();

      for (int var3 = var1.size() - 1; var3 >= 0 && var2.size() < 20000; var3--) {
         PackResources var4 = (PackResources)var1.get(var3);

         try {
            for (String var6 : var4.getNamespaces(PackType.CLIENT_RESOURCES)) {
               if ((String)com.yiyiaddon.m.b.a<"s25hdaq8q868u7","SgtMONj9/L1MoI+CiSZmdkCa5vl7xl0JyCS+ZL12OEq5hjCqOjcAZCsCTWSClZFnv0s=",-1515212504411424845,-4321857707406360986,-7344567310339663243,-4429688726678671116>()
                  .equals(var6)) {
                  for (String var10 : V) {
                     if (var2.size() >= 20000) {
                        break;
                     }

                     a(var4, var6, var10, var2);
                  }
               }
            }
         } catch (Exception var12) {
         }
      }

      return var2.isEmpty() ? null : b(var2);
   }

   private static void a(PackResources var0, String var1, String var2, Map<String, String> var3) {
      try {
         var0.listResources(
            PackType.CLIENT_RESOURCES,
            var1,
            var2,
            (var1x, var2x) -> {
               if (var3.size() >= 20000) {
                  switch ((int)com.yiyiaddon.m.b.a<"sx5ci4mzd88qb","VLHpRTzq7pFRjnJymmv8ME7eGCMNf0mcwLyIUvGRXa4=",5173475192387395566,-3898667574674999486,1528538637682765458,7085014586979161810>()) {
                     case 95585195:
                        return;
                     default:
                        throw null;
                  }
               } else {
                  String var3x = var1x.toString();
                  if (var3.containsKey(var3x)) {
                     switch ((int)com.yiyiaddon.m.b.a<"sipodzp5yk956","NDk6u2rM+RlFU/7lY8gAmCoerd9g4cjbnB8t/w1LFy4=",-6047403666987177081,320822446189123267,-3713092392533977211,-2557575938127766997>()) {
                        case 1969345327:
                           return;
                        default:
                           throw null;
                     }
                  } else {
                     var3.put(var3x, b(var2x));
                  }
               }
            }
         );
      } catch (Exception var5) {
      }
   }

   private static String b(IoSupplier<InputStream> var0) {
      try (InputStream var1 = (InputStream)var0.get()) {
         if (var1 == null) {
            return (String)com.yiyiaddon.m.b.a<"s349w7ho0innp6","fJfFIzUeYqT+IwGe894sQ2bWNq7UuWYpmJY1a+ZjJMkvTyHBpIKyxVTbasD+xnXV",-8186102042488298019,1776018577584426406,5388809743956920989,-730756295116450121>();
         }

         MessageDigest var2 = MessageDigest.getInstance(
            (String)com.yiyiaddon.m.b.a<"supxzwat9mhub","OBycc+MElvHcZgmEQ8gPqycxWx0d2eHa30oAwq8C3fQushrlv7EkvNhL",769968314036701676,6465554476992942492,-7695194771994471928,1347669865300788722>()
         );
         byte[] var3 = new byte[8192];

         int var4;
         while ((var4 = var1.read(var3)) != -1) {
            var2.update(var3, 0, var4);
         }

         return HexFormat.of().formatHex(var2.digest(), 0, 8);
      } catch (Exception var8) {
         return (String)com.yiyiaddon.m.b.a<"s349w7ho0innp6","fJfFIzUeYqT+IwGe894sQ2bWNq7UuWYpmJY1a+ZjJMkvTyHBpIKyxVTbasD+xnXV",-8186102042488298019,1776018577584426406,5388809743956920989,-730756295116450121>();
      }
   }

   private static String b(Map<String, String> var0) {
      ArrayList var1 = new ArrayList(var0.keySet());
      Collections.sort(var1);

      try {
         MessageDigest var2 = MessageDigest.getInstance(
            (String)com.yiyiaddon.m.b.a<"supxzwat9mhub","OBycc+MElvHcZgmEQ8gPqycxWx0d2eHa30oAwq8C3fQushrlv7EkvNhL",769968314036701676,6465554476992942492,-7695194771994471928,1347669865300788722>()
         );

         for (String var4 : var1) {
            var2.update(var4.getBytes(StandardCharsets.UTF_8));
            var2.update((byte)0);
            var2.update(((String)var0.get(var4)).getBytes(StandardCharsets.UTF_8));
            var2.update((byte)10);
         }

         return HexFormat.of().formatHex(var2.digest(), 0, 6);
      } catch (Exception var5) {
         return null;
      }
   }
}
