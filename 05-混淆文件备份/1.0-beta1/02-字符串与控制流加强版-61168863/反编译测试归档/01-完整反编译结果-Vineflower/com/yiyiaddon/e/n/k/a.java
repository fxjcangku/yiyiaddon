package com.yiyiaddon.e.n.k;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yiyiaddon.k.e.c;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;

public final class a {
   public static final int mH = 32;
   public static final int mI = 64;
   public static final String tE = "mixed";
   public static final String tF = "混种";
   private final Path k;
   private final List<com.yiyiaddon.e.n.k.a.b> bQ = new ArrayList<>();
   private String nc = null;
   private boolean dK;

   public a() {
      this.k = Minecraft.getInstance()
         .gameDirectory
         .toPath()
         .resolve(
            (String)com.yiyiaddon.m.b.a<"s3bcm2ddc2rex8","CbGPGVZdJeLoQT0Ny/vtVKzKQOYiqog+no41D4wo2KjHzFkedPdPr1Tg4dJdWNZr+PI=",1155025135916258892,-3849510988130266807,6612415868832910713,-4621988688127452565>()
         )
         .resolve(
            (String)com.yiyiaddon.m.b.a<"s2feypaj0jqskl","yC726R0zXi2vY+rnrPgzhL8TgMsSALIrGXPZxIdj76OUQ3OCB0lfXPJW",-322333052496183365,6578832853172564854,6980609801477915105,-2646948810761194507>()
         );
   }

   private Path b(String var1) {
      String var2 = var1.replaceAll(
         (String)com.yiyiaddon.m.b.a<"sh094405lywcr","0INRJdac/sd7I+xuQY9ZaU21U+04SXKE8yUjOYHtnu4bcLc6iz45NWE5dynCFokVcqMnMA==",7185247781290571093,5557120411482438963,-2092406557972741275,7299686451353692252>(),
         (String)com.yiyiaddon.m.b.a<"s3g71ntd90s5rv","wwpqtbSlOvP9Ns9Q3La1K5H8SzeCQJUSlT1XnBoE",6782746574217564909,-1197254634871545987,-1118486761939535247,-5286891881303402733>()
      );
      return this.k.resolve(var2 + "");
   }

   public synchronized boolean du() {
      return this.dK;
   }

   public synchronized void au(String var1) {
      if (var1 != null && !var1.equals(this.nc)) {
         this.nc = var1;
         this.dK = false;
         this.bQ.clear();
         Path var2 = this.b(var1);
         if (!Files.isRegularFile(var2)) {
            this.dK = true;
         } else {
            try {
               JsonObject var3 = JsonParser.parseString(Files.readString(var2, StandardCharsets.UTF_8)).getAsJsonObject();
               if (!var1.equals(
                  c(
                     var3,
                     (String)com.yiyiaddon.m.b.a<"s3m2vvcr8no4v2","YtMl3vaKnSe0O03LS20FxzbYWg+zfPZE+TvoPq+nrNV+xA==",-1755292212768793667,934400967367899158,585042534624584294,-724196362164504991>()
                  )
               )) {
                  return;
               }

               if (!var3.has(
                     (String)com.yiyiaddon.m.b.a<"s3c4q2zvtkmvla","ao4c9Qe8C3lRXq8egV1+/ARzyDX3RVlrzWjxIEXOto4=",2971032411782996091,1672129480248713310,6937944079052247142,1056198747833917603>()
                  )
                  || !var3.get(
                        (String)com.yiyiaddon.m.b.a<"s3c4q2zvtkmvla","ao4c9Qe8C3lRXq8egV1+/ARzyDX3RVlrzWjxIEXOto4=",2971032411782996091,1672129480248713310,6937944079052247142,1056198747833917603>()
                     )
                     .isJsonArray()) {
                  return;
               }

               for (JsonElement var5 : var3.getAsJsonArray(
                  (String)com.yiyiaddon.m.b.a<"s3c4q2zvtkmvla","ao4c9Qe8C3lRXq8egV1+/ARzyDX3RVlrzWjxIEXOto4=",2971032411782996091,1672129480248713310,6937944079052247142,1056198747833917603>()
               )) {
                  if (var5.isJsonObject()) {
                     JsonObject var6 = var5.getAsJsonObject();
                     com.yiyiaddon.e.n.k.a.b var7 = a(var6);
                     if (var7 != null) {
                        this.bQ.add(var7);
                     }
                  }
               }

               this.dK = true;
            } catch (Exception var8) {
            }
         }
      }
   }

   private static com.yiyiaddon.e.n.k.a.b a(JsonObject var0) {
      try {
         boolean var1 = b(
            var0,
            (String)com.yiyiaddon.m.b.a<"scl244uqlck2j","xFrGwE5XZ6y7eH63yiOrElfKG5qvkXTelUGMFKM0euI=",871389704291182044,4697985738149450705,-6948405417584315214,5882185860086960240>(),
            false
         );
         String var2 = var1
            ? (String)com.yiyiaddon.m.b.a<"s2lfgiuvgvy8yo","c6B06oXquVwmgWSpXaXbmXxyGF4gkxtTuIHeF65RQVOj3Yi0/DI=",7211594102109118373,-8391710168210067575,-5640177140285859041,-2062869722719160987>()
            : c(
               var0,
               (String)com.yiyiaddon.m.b.a<"s3rf5kw4widszd","0xKXtvdxjcjIepmdrIQiplBqHWxMOsPiaaJp6OXl367dKw==",8673279350624847831,-6012052597823051372,-7766286372499167701,-4779415132547320551>()
            );
         String var3 = var1
            ? (String)com.yiyiaddon.m.b.a<"scl244uqlck2j","xFrGwE5XZ6y7eH63yiOrElfKG5qvkXTelUGMFKM0euI=",871389704291182044,4697985738149450705,-6948405417584315214,5882185860086960240>()
            : c(
               var0,
               (String)com.yiyiaddon.m.b.a<"s6asn5smguany","OFcy2mt/lb3MO5K2jQYeQqpCPT0S1B/vFlmEzWGhjKM=",-4500050635090618264,8136208790642496096,4940250000845215940,-4578463360836217764>()
            );
         return var2 != null && var3 != null
            ? new com.yiyiaddon.e.n.k.a.b(
               b(
                  var0,
                  (String)com.yiyiaddon.m.b.a<"suwofl8msqqq5","5tZdNB0tvrt3/emiu5KqgiqS1kuMl84htJNQ5qs7CDU=",3388828930199535143,1659586611545417112,6550321450534794431,-8181116073158679388>(),
                  0
               ),
               var1,
               var2,
               var3,
               c(
                  var0,
                  (String)com.yiyiaddon.m.b.a<"svlm7dz9rd2c7","ryFFnAx7T2i8NdfIuBYZpvp/BiRpYxCIyXxXwUz9ihQ=",780417004659496426,6698935420132521101,946320065235385095,3790839581778160063>()
               ),
               b(
                  var0,
                  (String)com.yiyiaddon.m.b.a<"s3i3fr1ywc22xy","UIvUyYPK28xuxcgP9Vo1oOG/SBfyRfbiM94JbNe/t4U=",5924314831683164460,3601471898327426633,-6727796846874109834,8970145445760645229>(),
                  0
               ),
               b(
                  var0,
                  (String)com.yiyiaddon.m.b.a<"s1mrm3zw26nirr","lHFf47uCwu7fH2QlVIRSzi6Um7yL6zJXTzQCLwG+1ro=",4913985242099988937,-5506825519740455289,6818729843892451077,8329551718932470933>(),
                  0
               ),
               b(
                  var0,
                  (String)com.yiyiaddon.m.b.a<"s3ul7sieg5ixg4","6PHndaiTilBWPL3Y0LRj/MtaRie9hEN7n/8HmxRZAOE=",-3804251472294746399,2434041411031490550,4500901525439652245,-2499142199779095560>(),
                  0
               ),
               b(
                  var0,
                  (String)com.yiyiaddon.m.b.a<"sw1ie50ta8y5n","LCdIKvnQN9ifQGVNZxUtv3tBJNmWYbJmJjZDRhpORI8=",-1070816484074465337,-1593473391789780722,2472232742264998592,5594140098481507881>(),
                  0
               ),
               b(
                  var0,
                  (String)com.yiyiaddon.m.b.a<"s3c9xg61uvk84a","KRC6gKJJm5tV7oeG2D5OW/WAm9oqiRpMuj/FlSsCISs=",6768898526629396068,1338001879215930750,-560988894140009369,5569141921407840340>(),
                  0
               ),
               b(
                  var0,
                  (String)com.yiyiaddon.m.b.a<"s1e33lzqv7otez","YHd02H4x6eNQCQuXKrkwzuN0jJkPsNcugU9k4YRWBV4=",-748297380909642177,-3612661315605758359,1980153502821399678,4124531649628742231>(),
                  0
               )
            )
            : null;
      } catch (Exception var4) {
         return null;
      }
   }

   public synchronized boolean V(String var1) {
      if (var1 == null || !var1.equals(this.nc)) {
         return false;
      }

      if (!this.dK) {
         return false;
      }

      JsonObject var2 = new JsonObject();
      var2.addProperty(
         (String)com.yiyiaddon.m.b.a<"s3m2vvcr8no4v2","YtMl3vaKnSe0O03LS20FxzbYWg+zfPZE+TvoPq+nrNV+xA==",-1755292212768793667,934400967367899158,585042534624584294,-724196362164504991>(),
         var1
      );
      var2.addProperty(
         (String)com.yiyiaddon.m.b.a<"sau1k7jyn2cwr","ruE9ism5AQZv15GjUdkUnY2m+2/fyOjVc2u0qsS4ktxizR7h",2416279944005690756,-5738870547200503509,2521039226459696499,4662206165572354184>(),
         c.dn()
      );
      JsonArray var3 = new JsonArray();

      for (com.yiyiaddon.e.n.k.a.b var5 : this.bQ) {
         JsonObject var6 = new JsonObject();
         var6.addProperty(
            (String)com.yiyiaddon.m.b.a<"suwofl8msqqq5","5tZdNB0tvrt3/emiu5KqgiqS1kuMl84htJNQ5qs7CDU=",3388828930199535143,1659586611545417112,6550321450534794431,-8181116073158679388>(),
            var5.cr()
         );
         var6.addProperty(
            (String)com.yiyiaddon.m.b.a<"scl244uqlck2j","xFrGwE5XZ6y7eH63yiOrElfKG5qvkXTelUGMFKM0euI=",871389704291182044,4697985738149450705,-6948405417584315214,5882185860086960240>(),
            var5.dv()
         );
         var6.addProperty(
            (String)com.yiyiaddon.m.b.a<"s6asn5smguany","OFcy2mt/lb3MO5K2jQYeQqpCPT0S1B/vFlmEzWGhjKM=",-4500050635090618264,8136208790642496096,4940250000845215940,-4578463360836217764>(),
            var5.ef()
         );
         var6.addProperty(
            (String)com.yiyiaddon.m.b.a<"s3rf5kw4widszd","0xKXtvdxjcjIepmdrIQiplBqHWxMOsPiaaJp6OXl367dKw==",8673279350624847831,-6012052597823051372,-7766286372499167701,-4779415132547320551>(),
            var5.dk()
         );
         var6.addProperty(
            (String)com.yiyiaddon.m.b.a<"svlm7dz9rd2c7","ryFFnAx7T2i8NdfIuBYZpvp/BiRpYxCIyXxXwUz9ihQ=",780417004659496426,6698935420132521101,946320065235385095,3790839581778160063>(),
            var5.bU()
         );
         var6.addProperty(
            (String)com.yiyiaddon.m.b.a<"s3i3fr1ywc22xy","UIvUyYPK28xuxcgP9Vo1oOG/SBfyRfbiM94JbNe/t4U=",5924314831683164460,3601471898327426633,-6727796846874109834,8970145445760645229>(),
            var5.cs()
         );
         var6.addProperty(
            (String)com.yiyiaddon.m.b.a<"s1mrm3zw26nirr","lHFf47uCwu7fH2QlVIRSzi6Um7yL6zJXTzQCLwG+1ro=",4913985242099988937,-5506825519740455289,6818729843892451077,8329551718932470933>(),
            var5.ct()
         );
         var6.addProperty(
            (String)com.yiyiaddon.m.b.a<"s3ul7sieg5ixg4","6PHndaiTilBWPL3Y0LRj/MtaRie9hEN7n/8HmxRZAOE=",-3804251472294746399,2434041411031490550,4500901525439652245,-2499142199779095560>(),
            var5.cu()
         );
         var6.addProperty(
            (String)com.yiyiaddon.m.b.a<"sw1ie50ta8y5n","LCdIKvnQN9ifQGVNZxUtv3tBJNmWYbJmJjZDRhpORI8=",-1070816484074465337,-1593473391789780722,2472232742264998592,5594140098481507881>(),
            var5.cv()
         );
         var6.addProperty(
            (String)com.yiyiaddon.m.b.a<"s3c9xg61uvk84a","KRC6gKJJm5tV7oeG2D5OW/WAm9oqiRpMuj/FlSsCISs=",6768898526629396068,1338001879215930750,-560988894140009369,5569141921407840340>(),
            var5.cw()
         );
         var6.addProperty(
            (String)com.yiyiaddon.m.b.a<"s1e33lzqv7otez","YHd02H4x6eNQCQuXKrkwzuN0jJkPsNcugU9k4YRWBV4=",-748297380909642177,-3612661315605758359,1980153502821399678,4124531649628742231>(),
            var5.cx()
         );
         var3.add(var6);
      }

      var2.add(
         (String)com.yiyiaddon.m.b.a<"s3c4q2zvtkmvla","ao4c9Qe8C3lRXq8egV1+/ARzyDX3RVlrzWjxIEXOto4=",2971032411782996091,1672129480248713310,6937944079052247142,1056198747833917603>(),
         var3
      );
      Path var7 = this.b(var1);
      d(var7);
      return com.yiyiaddon.j.a.a(var7, var2);
   }

   private static void d(Path var0) {
      if (Files.isRegularFile(var0)) {
         try {
            Files.copy(var0, var0.resolveSibling(var0.getFileName() + ""), StandardCopyOption.REPLACE_EXISTING);
         } catch (Exception var2) {
         }
      }
   }

   public synchronized List<com.yiyiaddon.e.n.k.a.b> c() {
      ArrayList var1 = new ArrayList<>(this.bQ);
      var1.sort(Comparator.comparingInt(com.yiyiaddon.e.n.k.a.b::cr));
      return List.copyOf(var1);
   }

   public synchronized List<com.yiyiaddon.e.n.k.a.b> g(String var1) {
      ArrayList var2 = new ArrayList();

      for (com.yiyiaddon.e.n.k.a.b var4 : this.c()) {
         if (Objects.equals(var4.bU(), var1)) {
            var2.add(var4);
         }
      }

      return var2;
   }

   public synchronized int b() {
      return this.bQ.size();
   }

   public synchronized com.yiyiaddon.e.n.k.a.b a(BlockPos var1, String var2) {
      if (var1 == null) {
         return null;
      }

      for (com.yiyiaddon.e.n.k.a.b var4 : this.c()) {
         if (Objects.equals(var4.bU(), var2) && var4.k(var1)) {
            return var4;
         }
      }

      return null;
   }

   public synchronized com.yiyiaddon.e.n.k.a.b a(com.yiyiaddon.e.n.k.a.b var1, String var2) {
      for (com.yiyiaddon.e.n.k.a.b var4 : this.c()) {
         if (Objects.equals(var4.bU(), var2) && var4.a(var1) > 0) {
            return var4;
         }
      }

      return null;
   }

   public synchronized com.yiyiaddon.e.n.k.a.a a(String var1, String var2, String var3, String var4, BlockPos var5, BlockPos var6) {
      this.au(var1);
      if (!this.dK) {
         return new com.yiyiaddon.e.n.k.a.a(null, this.b(var1).getFileName() + "");
      }

      if (var5.getX() == var6.getX() && var5.getZ() == var6.getZ()) {
         return new com.yiyiaddon.e.n.k.a.a(
            null,
            (String)com.yiyiaddon.m.b.a<"s4athizwmh1og","TL2YJasjJsgM0he0GoyRu2zLxtho2yupffwS63YTbLsu4UrB3oJzNiimHjU3VywCIAGqWnNs",-4886683848720605298,-6777881638692859112,1728830681250197046,33611602168773002>()
         );
      }

      int var7 = Math.abs(var5.getX() - var6.getX()) + 1;
      int var8 = Math.abs(var5.getZ() - var6.getZ()) + 1;
      if (var7 <= 64 && var8 <= 64) {
         if (this.bQ.size() >= 32) {
            return new com.yiyiaddon.e.n.k.a.a(
               null,
               (String)com.yiyiaddon.m.b.a<"s39ivhh3yp0caf","U+LMyUnlU7eYF+3R/wFUT+SIaf60aAhBcgyoGXxuWHkQksueE2mGJycm0+ji//D04mVXn72zfWrzRYxE+SctlJchozZ/hw==",-27914335750559001,-5904022064337604621,1007675984046725512,7956512385692203022>()
            );
         }

         boolean var9 = var2 == null
            || (String)com.yiyiaddon.m.b.a<"s2lfgiuvgvy8yo","c6B06oXquVwmgWSpXaXbmXxyGF4gkxtTuIHeF65RQVOj3Yi0/DI=",7211594102109118373,-8391710168210067575,-5640177140285859041,-2062869722719160987>()
               .equals(var2);
         com.yiyiaddon.e.n.k.a.b var10 = new com.yiyiaddon.e.n.k.a.b(
            this.ch(),
            var9,
            var9
               ? (String)com.yiyiaddon.m.b.a<"s2lfgiuvgvy8yo","c6B06oXquVwmgWSpXaXbmXxyGF4gkxtTuIHeF65RQVOj3Yi0/DI=",7211594102109118373,-8391710168210067575,-5640177140285859041,-2062869722719160987>()
               : var2,
            var9
               ? (String)com.yiyiaddon.m.b.a<"scl244uqlck2j","xFrGwE5XZ6y7eH63yiOrElfKG5qvkXTelUGMFKM0euI=",871389704291182044,4697985738149450705,-6948405417584315214,5882185860086960240>()
               : var3,
            var4,
            var5.getX(),
            var5.getY(),
            var5.getZ(),
            var6.getX(),
            var6.getY(),
            var6.getZ()
         );
         com.yiyiaddon.e.n.k.a.b var11 = this.a(var10, var4);
         if (var11 != null) {
            return new com.yiyiaddon.e.n.k.a.a(null, var11.cr() + var11.ef() + var11.a(var10));
         }

         this.bQ.add(var10);
         this.V(var1);
         return new com.yiyiaddon.e.n.k.a.a(var10, null);
      } else {
         return new com.yiyiaddon.e.n.k.a.a(null, "" + var7 + var8);
      }
   }

   public synchronized boolean a(String var1, int var2) {
      this.au(var1);
      if (!this.dK) {
         return false;
      }

      boolean var3 = this.bQ
         .removeIf(
            var1x -> {
               if (var1x.cr() == var2) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2xiqscs4ihk93","OXWoCLMabBQ5d6+A3UwLDsq/FddHr/7XbiWrSDA6ZMk=",1182370104527943569,1459435197236319160,4186029146538926423,7210710864344280332>()) {
                     case 1288431941:
                        switch ((int)com.yiyiaddon.m.b.a<"s15v148ujyj8wh","iaqvXvf6LqFMyp3IHk2UUT5eUtbH2C6DVFqvPA8REf0=",3707977687565157148,8054285130069546610,-1979699480294726329,1617311288779098026>()) {
                           case 1624688722:
                              return true;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s12fun6ni60e6o","TisOdsiDGTQHDId4hFpiKoN5xyjp4qpOMP406RdIVuw=",161812246097548933,-1047039268787396913,-6392014831297219304,5826216101477243773>()) {
                     case -1076478891:
                        return false;
                     default:
                        throw null;
                  }
               }
            }
         );
      if (var3) {
         this.V(var1);
      }

      return var3;
   }

   public synchronized boolean a(String var1, int var2, String var3, String var4) {
      if (var3 != null
         && !var3.isBlank()
         && !(String)com.yiyiaddon.m.b.a<"s2lfgiuvgvy8yo","c6B06oXquVwmgWSpXaXbmXxyGF4gkxtTuIHeF65RQVOj3Yi0/DI=",7211594102109118373,-8391710168210067575,-5640177140285859041,-2062869722719160987>()
            .equals(var3)
         && var4 != null
         && !var4.isBlank()) {
         this.au(var1);
         if (!this.dK) {
            return false;
         }

         for (int var5 = 0; var5 < this.bQ.size(); var5++) {
            com.yiyiaddon.e.n.k.a.b var6 = this.bQ.get(var5);
            if (var6.cr() == var2) {
               this.bQ
                  .set(
                     var5,
                     new com.yiyiaddon.e.n.k.a.b(var6.cr(), false, var3, var4, var6.bU(), var6.cs(), var6.ct(), var6.cu(), var6.cv(), var6.cw(), var6.cx())
                  );
               this.V(var1);
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public synchronized int i(String var1) {
      this.au(var1);
      if (!this.dK) {
         return 0;
      }

      int var2 = this.bQ.size();
      this.bQ.clear();
      this.V(var1);
      return var2;
   }

   private int ch() {
      int var1 = 0;
      Iterator var2 = this.bQ.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s2kipcnsbacsjq","/W39ao9AN2fpyRPKv/KeSywgCeoBKiClp4RBdqy1uL4=",6307009904070720521,3040990044649730851,-4670969870844866501,-4284169070507896903>()) {
         case -1924877994:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s1q4dw5l2ci6ea","YFLISqGxh1kgekrkD/O3gjVSd0rVQp9/PMWZ4lPGHOs=",2789655231939002425,2453820076828386932,4461955028937259553,1367711791404055138>()) {
                  case -662554576:
                     com.yiyiaddon.e.n.k.a.b var3 = (com.yiyiaddon.e.n.k.a.b)var2.next();
                     var1 = Math.max(var1, var3.cr());
                     switch ((int)com.yiyiaddon.m.b.a<"s1ppm63rzeufd5","vHjKlDOVXSm+72D/cVFcfKVj4ktU9Dvkw6fqIH0nfes=",4905857607047431092,2837650553756814405,3760786351004139310,1203814074582683551>()) {
                        case -1703072368:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return var1 + 1;
         default:
            throw null;
      }
   }

   public synchronized void cy() {
      this.bQ.clear();
      this.nc = null;
      this.dK = false;
   }

   private static String c(JsonObject var0, String var1) {
      if (var0 != null && var0.has(var1) && !var0.get(var1).isJsonNull()) {
         try {
            String var2 = var0.get(var1).getAsString();
            return var2 != null && !var2.isBlank() ? var2 : null;
         } catch (Exception var3) {
            return null;
         }
      } else {
         return null;
      }
   }

   private static int b(JsonObject var0, String var1, int var2) {
      try {
         return var0.has(var1) && var0.get(var1).isJsonPrimitive() ? var0.get(var1).getAsInt() : var2;
      } catch (Exception var4) {
         return var2;
      }
   }

   private static boolean b(JsonObject var0, String var1, boolean var2) {
      try {
         return var0.has(var1) && var0.get(var1).isJsonPrimitive() ? var0.get(var1).getAsBoolean() : var2;
      } catch (Exception var4) {
         return var2;
      }
   }

   public record a(com.yiyiaddon.e.n.k.a.b a, String tG) {
      public boolean b() {
         if (this.a != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s1l771d6629zj5","Rs0L9xE6nVlP8rtTliuQykvxJxzqvCuqZc5t/KTBQMc=",-4165963291928124546,-3241469208138530587,7187291054370406096,-4087026639953265937>()) {
               case 348003214:
                  switch ((int)com.yiyiaddon.m.b.a<"s3rmooxloaijno","0ddjNdPM3SihA+l+Bm26thy4kzUQHXbNklwTPQjgHnI=",4330023882307007708,4619758157863072834,-5572175333612753800,-8715573899553177603>()) {
                     case -976324544:
                        return true;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            switch ((int)com.yiyiaddon.m.b.a<"snz4z0j7isev9","cmlOmHry3DbZcyBGgPT+Hggq9f7/Xj+fVsZMv4oqyvY=",-4841774861623125282,-5342194466137225933,-2664842473809084086,8662834670904538646>()) {
               case 460765642:
                  return false;
               default:
                  throw null;
            }
         }
      }

      public String ed() {
         return this.tG;
      }
   }

   public record b(int mJ, boolean dL, String tH, String tI, String tJ, int mK, int mL, int mM, int mN, int mO, int mP) {
      public int ci() {
         return Math.min(this.mK, this.mN);
      }

      public int cj() {
         return Math.max(this.mK, this.mN);
      }

      public int ck() {
         return Math.min(this.mL, this.mO);
      }

      public int cl() {
         return Math.max(this.mL, this.mO);
      }

      public int cm() {
         return Math.min(this.mM, this.mP);
      }

      public int cn() {
         return Math.max(this.mM, this.mP);
      }

      public int co() {
         return this.cj() - this.ci() + 1;
      }

      public int cp() {
         return this.cn() - this.cm() + 1;
      }

      public int cq() {
         return this.co() * this.cp();
      }

      public boolean d(int var1, int var2) {
         if (var1 >= this.ci()) {
            switch ((int)com.yiyiaddon.m.b.a<"s2kqiojcqtrs7n","qbc+ckL9jC5uMbbuMqJmUNvbbmiF7067nRBBu4yg2S4=",-8093680490229480839,-3316815346521406346,-1940045997399081899,-6259943333234597102>()) {
               case -1934539095:
                  if (var1 <= this.cj()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1nf2uc6ewxa5s","jHXDnQZbAQ3bq6rMOZDr9rdB0k6Pri4PHEGzHD3gXMM=",-1902843223606269897,2624134102706709999,8987473734419130919,-5838647081270489714>()) {
                        case 789305733:
                           if (var2 >= this.cm()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3c712o22kl7ci","fL4Kz3IyTGHk99pNsvP+jqZJ9GO7I4/On5NuyEnoBIk=",1420227899840612963,1623502426557290979,-5530946660574792546,-5794363238600116185>()) {
                                 case 638785167:
                                    if (var2 <= this.cn()) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s33qqzz3khport","q5+PPy7iI3u2q2GvEIz7G5hY9A4k5EggpGFIAI41YBE=",-6577064686595505501,-661870943525222045,2143075205164759647,4118795685558647433>()) {
                                          case -1557595627:
                                             switch ((int)com.yiyiaddon.m.b.a<"s1wvl3dmlb45p","rt5mi0mfXs2rM7KeWi0/fij3TNb4U/C6xwexzYr6VOI=",-6049447491673869248,-289886110547628035,-1783254504767197022,-7424346549853670519>()) {
                                                case -750892222:
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

         switch ((int)com.yiyiaddon.m.b.a<"s2h2isc2v008vb","kvYG8FUuqxEcjJXDTOm3T9ppA3O5pJgmxNgzCaJXu1U=",7597368414445170499,922022174962224532,4235262910746930891,1527865498198529693>()) {
            case -480686845:
               return false;
            default:
               throw null;
         }
      }

      public boolean k(BlockPos var1) {
         if (var1 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"sr203whs7g0tt","GaaKJWrbSK7Z5Sy2VIT9GyTnTUIW7HYR9famfbvf+2w=",815768827472515697,5484059202718123057,-2519856275146998269,7913445693710780162>()) {
               case -2843909:
                  if (this.d(var1.getX(), var1.getZ())) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2jav8gxjagu2k","Zp/1YxT6kJx1OXamf8I/a5LbInvcfFhdviVwPnqNtW0=",6789522908356544137,3560319559446886320,-343646214774593451,-4997227385810726342>()) {
                        case 486009797:
                           switch ((int)com.yiyiaddon.m.b.a<"s3embd3dj186sg","fXHXYOLL5oeId1rxjqtBx+KyHoSfDfcWg6L93mffkrM=",8114266115726230383,3118448506627624817,-5060432311301508304,-1390667027826095096>()) {
                              case 1864694121:
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

         switch ((int)com.yiyiaddon.m.b.a<"s2sqy31uvwhh08","Jxa+x1Miw2TOJzKczXkz5amhguFpVjqcaWCFQQ2QIcQ=",-6185202364323621810,8983033741825150865,6098647490781426388,-8019623226353951300>()) {
            case -583813076:
               return false;
            default:
               throw null;
         }
      }

      public int a(com.yiyiaddon.e.n.k.a.b var1) {
         if (var1 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s2lwjr4mruk61i","Ovgek7iQ4JjNJu2X0oobYS6CqnFU6ilRQFY+83Brma8=",3622375998398158890,3045591162742018454,7010238221484536346,8531620983818523030>()) {
               case -1393859722:
                  return 0;
               default:
                  throw null;
            }
         } else {
            int var2 = Math.min(this.cj(), var1.cj()) - Math.max(this.ci(), var1.ci()) + 1;
            int var3 = Math.min(this.cn(), var1.cn()) - Math.max(this.cm(), var1.cm()) + 1;
            if (var2 > 0) {
               label27:
               switch ((int)com.yiyiaddon.m.b.a<"s3mumnlmcd001c","BR32IOSbqzIUDFS0qsrv3JQLkRkreO1ffVaTbxWE2sc=",-8300688290329511085,8900728263869204417,-4708324159039412423,-4713273415264689884>()) {
                  case 1034483534:
                     if (var3 > 0) {
                        int var10000 = var2 * var3;
                        switch ((int)com.yiyiaddon.m.b.a<"s19kuj7drg5dyr","s8wk+rIZufji+2lMoyS1h0Oh+DoLr1eKmUZepBi3hDY=",-2575395386453062267,2250133090746969557,-1827215341941300318,-6267217322674326186>()) {
                           case -2051262752:
                              return var10000;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"spqi0yav3twek","ozjf9jEReA7F66SiYbK2O9HHTAcD3m7t9SnjPHmU62I=",-245931084239591245,-3681774406099501835,-2229024866887753935,8312219685352473878>()) {
                        case -429811727:
                           break label27;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            switch ((int)com.yiyiaddon.m.b.a<"s5v5cdkx926zm","Qh2f0IvmMho1425pblF0aDGWAnOnKN7OdZ8Y9NKLeCQ=",1153464051691158548,701625206105998091,-2884841563702966261,5473384340993347325>()) {
               case 2028165783:
                  return 0;
               default:
                  throw null;
            }
         }
      }

      public String ee() {
         return "" + this.ci() + this.cj() + this.cm() + this.cn();
      }

      public BlockPos q() {
         return new BlockPos((this.ci() + this.cj()) / 2, this.cl() + 1, (this.cm() + this.cn()) / 2);
      }

      public int cr() {
         return this.mJ;
      }

      public boolean dv() {
         return this.dL;
      }

      public String dk() {
         return this.tH;
      }

      public String ef() {
         return this.tI;
      }

      public String bU() {
         return this.tJ;
      }

      public int cs() {
         return this.mK;
      }

      public int ct() {
         return this.mL;
      }

      public int cu() {
         return this.mM;
      }

      public int cv() {
         return this.mN;
      }

      public int cw() {
         return this.mO;
      }

      public int cx() {
         return this.mP;
      }
   }
}
