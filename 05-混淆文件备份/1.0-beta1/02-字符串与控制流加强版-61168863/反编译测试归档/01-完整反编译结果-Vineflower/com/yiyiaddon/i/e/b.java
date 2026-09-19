package com.yiyiaddon.i.e;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

public final class b {
   private b() {
   }

   public static com.yiyiaddon.i.e.b.b a() {
      ResourceManager var0 = d.a();
      if (var0 == null) {
         return com.yiyiaddon.i.e.b.b.a(
            (String)com.yiyiaddon.m.b.a<"s38qkh1mrvvi08","W2o+yFZ98Y2napAKaz4rLlsOKTKtTXQbTyoekbwehlhDPbheSsaBDFO0m4A=",6822044669524091283,-2581405668376606471,-1280281488559594937,172896748944863139>()
         );
      }

      try {
         LinkedHashSet var1 = new LinkedHashSet<>(var0.getNamespaces());
         Map var2 = var0.listResources(
            (String)com.yiyiaddon.m.b.a<"s1zmchb3ttzfb0","j9qQuz3NoATCzHniUzOr1GSlkK2QFpF3y1ebrA==",-3728803635974371529,6762112283343571105,-1551981404793096797,5844457198192877137>(),
            var0x -> true
         );
         ArrayList var3 = new ArrayList(var2.size());

         for (Entry var5 : var2.entrySet()) {
            Identifier var6 = (Identifier)var5.getKey();
            Resource var7 = (Resource)var5.getValue();
            if (var6 != null && var7 != null) {
               var3.add(new com.yiyiaddon.i.e.b.a(var6.getNamespace(), var6.getPath(), var7));
            }
         }

         var3.sort(Comparator.comparing(com.yiyiaddon.i.e.b.a::s));
         return new com.yiyiaddon.i.e.b.b(var1, var3, null);
      } catch (Throwable var8) {
         return com.yiyiaddon.i.e.b.b.a(
            var8.getClass().getSimpleName()
               + (
                  var8.getMessage() == null
                     ? (String)com.yiyiaddon.m.b.a<"s1zmchb3ttzfb0","j9qQuz3NoATCzHniUzOr1GSlkK2QFpF3y1ebrA==",-3728803635974371529,6762112283343571105,-1551981404793096797,5844457198192877137>()
                     : var8.getMessage() + ""
               )
         );
      }
   }

   public record a(String Ed, String Ee, Resource a) {
      public String s() {
         return this.Ed + this.Ee;
      }

      public String gk() {
         return this.Ed;
      }

      public String gl() {
         return this.Ee;
      }
   }

   public record b(Set<String> aE, List<com.yiyiaddon.i.e.b.a> cM, String Ef) {
      public boolean b() {
         if (this.Ef == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s3oxdiopt5yvdc","v2xvN231seDUfMvciMKKqm1SfwcfVkAufIk82gsqguw=",-2127993860151964066,-3814463742059310229,123803934010084930,5344395610622991936>()) {
               case 1013283967:
                  switch ((int)com.yiyiaddon.m.b.a<"s2kynerdzx082g","7nuoKm4Bt7J1/6fVdy9aM2qPHUEve6YugG2mDdNP0KI=",-1889609173508135680,-4685242446278451961,-2506443130380297173,-1156278774483864977>()) {
                     case -1667177333:
                        return true;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            switch ((int)com.yiyiaddon.m.b.a<"s3kixt7wpn3i4o","CTAf94Sthjz/PI3n0T2yODWd2msxUe6El5peGbzBaz4=",-3125808743382990005,7133114733975657950,6147072075948452581,-5134322325805414740>()) {
               case -520750933:
                  return false;
               default:
                  throw null;
            }
         }
      }

      public static com.yiyiaddon.i.e.b.b a(String var0) {
         return new com.yiyiaddon.i.e.b.b(Set.of(), List.of(), var0);
      }

      public Set<String> z() {
         return this.aE;
      }

      public List<com.yiyiaddon.i.e.b.a> D() {
         return this.cM;
      }

      public String bz() {
         return this.Ef;
      }
   }
}
