package com.yiyiaddon.e.n.h;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yiyiaddon.e.n.i.f;
import com.yiyiaddon.e.n.i.p;
import com.yiyiaddon.e.n.i.s;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.Container;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public final class c {
   private final Path h;
   private final Map<d, List<c.a>> T = new EnumMap<>(d.class);
   private String nc = null;
   public static final String rP = "所在区块尚未加载，无法验证";
   public static final String rQ = "容器数据尚未同步，无法验证";

   public c() {
      this.h = Minecraft.getInstance()
         .gameDirectory
         .toPath()
         .resolve(
            (String)com.yiyiaddon.m.b.a<"s3gyf07jw0i4wa","Nn3l5INGoSnbkCJIWtwbJtcGm6HiplnEA32U/9kRPKcC3Vr0be0Lw3xMDuksQ1LlBpE=",-7077464202969478966,-6548596318899368533,-254249905611461819,-5567685451619813874>()
         )
         .resolve(
            (String)com.yiyiaddon.m.b.a<"s1z86la9ormloi","IhCdiqHz/XfDbcu6BIGVjwi+Q/tNc/2mdr7Paadm/uAVpXGMmFPSCg==",2544309136727727322,8170070707895928503,-2976589100376034390,7042535812284751750>()
         );
   }

   private Path b(String var1) {
      String var2 = var1.replaceAll(
         (String)com.yiyiaddon.m.b.a<"s2wu023p7wyaog","Eo1k8XiT52c7Q5znx4DRlFnws/V6caudQm+dCZhmZcz3guW2aBiQ9RaNP85MzkA/Dth+vQ==",4796581423813757554,7636281608808731730,-2574577723367147499,1463332288170672854>(),
         (String)com.yiyiaddon.m.b.a<"s35isc4d4ruevo","0D72JLrjhCfNKw5z7zDJRXM+6/Jyqo/UKFlcWBzr",-2663721207449904535,2857979017868947822,-1366467339343323325,-4233703197472217035>()
      );
      return this.h.resolve(var2 + "");
   }

   public synchronized void au(String var1) {
      if (var1 != null && !var1.equals(this.nc)) {
         this.nc = var1;

         for (d var5 : d.values()) {
            this.T.put(var5, new ArrayList<>());
         }

         Path var13 = this.b(var1);
         if (Files.isRegularFile(var13)) {
            try {
               JsonObject var14 = JsonParser.parseString(Files.readString(var13, StandardCharsets.UTF_8)).getAsJsonObject();
               if (!var1.equals(
                  c(
                     var14,
                     (String)com.yiyiaddon.m.b.a<"s1tlkopfuofzu6","RjDqw/jKAXWnAv7zPUelE6C82yByv1+BCTaZ4ahQdJ/UxA==",1102640547224380859,3207734845838023046,1224861467030829823,-9209196745910838832>()
                  )
               )) {
                  return;
               }

               for (d var7 : d.values()) {
                  List var8 = this.T.get(var7);
                  var8.clear();
                  if (var14.has(var7.aM()) && var14.get(var7.aM()).isJsonArray()) {
                     for (JsonElement var10 : var14.getAsJsonArray(var7.aM())) {
                        if (var10.isJsonObject()) {
                           JsonObject var11 = var10.getAsJsonObject();
                           var8.add(
                              new c.a(
                                 var11.get(
                                       (String)com.yiyiaddon.m.b.a<"s3uawssjpe8aiy","z0U4DhLp5vT5RkzXMwUe4COuD9Qyc4z51tvI5yRp",-6895210920303827377,-849950857968556783,8614960931409984407,-3134827130101488895>()
                                    )
                                    .getAsInt(),
                                 var11.get(
                                       (String)com.yiyiaddon.m.b.a<"stz02qz6s6ztt","m5UkWDBcJfA0e+cmnRu81wL4OVMLL4q331POVsU5",5982697562945759647,5109405939166277955,-6953236412638900977,7855462427100016806>()
                                    )
                                    .getAsInt(),
                                 var11.get(
                                       (String)com.yiyiaddon.m.b.a<"soxz4j76ki0lp","RIhs3tQ4hdO+otT54tFQtU5OteApm0BsSqQMpIoT",8829118901759026247,-843597666067700585,-4132280556255594656,-9102924352574054651>()
                                    )
                                    .getAsInt(),
                                 c(
                                    var11,
                                    (String)com.yiyiaddon.m.b.a<"s11ik0yrqk6sm4","giBdy1SIs8GloVU0rX0XUaqGi4kRYSJKEwwaioA7QLg=",3775911194565373452,1521744841500249463,7794813873523221928,-8834901073845285997>()
                                 ),
                                 c(
                                    var11,
                                    (String)com.yiyiaddon.m.b.a<"s5l3uypqq4k03","5810wPygKo+LCzsFsBekew3eVi7CQmj41UnR991WqYQ=",-9035852674644016019,-7133081532407000569,-8485040907663306282,-2431754528871167527>()
                                 ),
                                 c(
                                    var11,
                                    (String)com.yiyiaddon.m.b.a<"s1wjn27s435xb3","hGNiwEfHxIFd8ONddOQqPzOgBj6dgeopsr0JW1yjpO8=",-6842481457979677477,-6497038796078540926,-3552847298449528178,-2042219848254975578>()
                                 ),
                                 c(
                                    var11,
                                    (String)com.yiyiaddon.m.b.a<"s7a57ufabo9wh","EY2O0r7tEEczfZYEXeH8G8ngygKbEEQAgqh0TiaEtrE=",1605421783167793656,-5471482191854274864,2321733667021828518,-1583157743861254302>()
                                 ),
                                 c(
                                    var11,
                                    (String)com.yiyiaddon.m.b.a<"s2185psno29fi6","nfZqKIm8Jwh06NI+wHYMDJ9L0zxff+Sqbbqc+E3yaLE=",3598797754755453705,8096519058262123969,8377741118548500831,5496976853997650348>()
                                 ),
                                 c(
                                          var11,
                                          (String)com.yiyiaddon.m.b.a<"s1tlkopfuofzu6","RjDqw/jKAXWnAv7zPUelE6C82yByv1+BCTaZ4ahQdJ/UxA==",1102640547224380859,3207734845838023046,1224861467030829823,-9209196745910838832>()
                                       )
                                       == null
                                    ? var1
                                    : c(
                                       var11,
                                       (String)com.yiyiaddon.m.b.a<"s1tlkopfuofzu6","RjDqw/jKAXWnAv7zPUelE6C82yByv1+BCTaZ4ahQdJ/UxA==",1102640547224380859,3207734845838023046,1224861467030829823,-9209196745910838832>()
                                    ),
                                 a(var11)
                              )
                           );
                        }
                     }
                  }
               }
            } catch (Exception var12) {
            }
         }
      }
   }

   private static String c(JsonObject var0, String var1) {
      if (var0.has(var1) && !var0.get(var1).isJsonNull()) {
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

   private static com.yiyiaddon.e.n.h.a a(JsonObject var0) {
      if (var0.has(
         (String)com.yiyiaddon.m.b.a<"su0accepxxqg5","geGrCohH5eYjS/AE5v7zriiynfl7EQvCGMbulDklKmQoH8w+",6139912327982408586,-4291072484017904546,3899854401113239243,-5759525814566262873>()
      )) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ceis4gve5ri4","piHsmq5dIq/RU8nGrXAbnn3yYDseUXJs4fCnTdtRNoc=",6308182057914765387,3718935920597227682,-972257966940543030,5066468883463050988>()) {
            case -1593274350:
               if (var0.get(
                     (String)com.yiyiaddon.m.b.a<"su0accepxxqg5","geGrCohH5eYjS/AE5v7zriiynfl7EQvCGMbulDklKmQoH8w+",6139912327982408586,-4291072484017904546,3899854401113239243,-5759525814566262873>()
                  )
                  .isJsonObject()) {
                  JsonObject var1 = var0.getAsJsonObject(
                     (String)com.yiyiaddon.m.b.a<"su0accepxxqg5","geGrCohH5eYjS/AE5v7zriiynfl7EQvCGMbulDklKmQoH8w+",6139912327982408586,-4291072484017904546,3899854401113239243,-5759525814566262873>()
                  );
                  String var2 = c(
                     var1,
                     (String)com.yiyiaddon.m.b.a<"s1tlkopfuofzu6","RjDqw/jKAXWnAv7zPUelE6C82yByv1+BCTaZ4ahQdJ/UxA==",1102640547224380859,3207734845838023046,1224861467030829823,-9209196745910838832>()
                  );
                  String var3 = c(
                     var1,
                     (String)com.yiyiaddon.m.b.a<"sz5ubcg1bgqk","tEnpB3c9V7slaogfTreALQMlc+I6fwTEtmDnYbCxchwE7WD6",8191126706902487922,-5575527693779907475,-3933144021385274042,-101440289811665772>()
                  );
                  String var4 = c(
                     var1,
                     (String)com.yiyiaddon.m.b.a<"s1f7wrv9b5paos","DAXwDTXWeDcBrvbxFgajBcyk7vrQayRMS0QwkIJNKhtWIrcnGS82UZ6Q",-3395169527031951460,-2911738444725683606,-8382717252391127920,-9057791078213946436>()
                  );
                  String var5 = c(
                     var1,
                     (String)com.yiyiaddon.m.b.a<"s2p6mm40lmvh01","8gXntTJx34A2ZnUXVBYmaH4N8x5++kn/rM1JeAg/YXFl1e1ngGnPyw==",-6819519111438480376,-5841716076367933432,-2489529516339083072,1017329615743517966>()
                  );
                  String var6 = c(
                     var1,
                     (String)com.yiyiaddon.m.b.a<"sguywies3yozo","VBG45AiT+l/gQtYUsMw2nCPOiAOyeV3u9eIcqT2qMmHyGoUTY+3aJg==",-1927629981025957112,-6371518417189341443,-7544584957386803591,525728007524760406>()
                  );
                  if (var2 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3dg4cdxpp82ww","LXyIrNFJ4krKbj/EzcFsde8Se97oNZvKabhonKyKbGc=",4431753598337293903,1536829186945006698,-2919799774123911823,-3902019938053870149>()) {
                        case 190783003:
                           if (var3 != null) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3abw3xsn0popo","SBlmthrYNLuGDn4cwC9ag2JfsWPB732FpZEZziD0t+g=",-7422531042427553891,96980114047165608,548877944832719456,3944797509857974657>()) {
                                 case 2027108443:
                                    if (var4 != null) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s2h4l6dx18uo2s","fQFBThIIRh7Y64xq7yeMA0MOVOVlVPLByEwYy9hJJ0E=",-706813974082343230,9090771258944489142,8871820972362268245,-8306520352569202334>()) {
                                          case 309829908:
                                             if (var5 != null) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s21vx737x2lcww","dQ0UcCJnbQywSJ01FguKqvwQE67/U66zRraN2r8v14Q=",-7487588882509430217,-1714608747195100908,-5555532709131480732,4438705285575753224>()) {
                                                   case -1734606369:
                                                      if (var6 != null) {
                                                         return new com.yiyiaddon.e.n.h.a(
                                                            var2,
                                                            var3,
                                                            var4,
                                                            var5,
                                                            var6,
                                                            c(
                                                               var1,
                                                               (String)com.yiyiaddon.m.b.a<"s347o4b38wqgug","ImiPjAzBWnOqo3mCzhxxKitVqqVqZx0GmfgMsrEABdF71dALuYA38Q==",-345303340740467158,-1079241394957227227,-6793608859638231772,6122477730695129975>()
                                                            ),
                                                            c(
                                                               var1,
                                                               (String)com.yiyiaddon.m.b.a<"s28uhwykw9raud","YokFPWtC5FDkkBu6pHkUjz+ZFvs9ZVhysOuTZPf2qQvNm9YC",943643361046028692,2692206095287380611,477878588213302037,5134480979336673002>()
                                                            )
                                                         );
                                                      }

                                                      switch ((int)com.yiyiaddon.m.b.a<"s3njqqcx97vapk","emImuIyPFECkg2j6kEmfCVl7+F1zXBW+RrNnrkpCrsA=",-1938480205428632939,4780005707686637926,-4080132209086359383,698854176879082513>()) {
                                                         case -1714285322:
                                                            return null;
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

                  return null;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3ub4rkrzrorhw","8C/knIvK4l3vMWTvdsLZPG7EVATGsQn4aJXDnR3hmZs=",-959784366237619387,-3387237583682506757,3609567889230027191,-3815902687951842041>()) {
                     case 1376260213:
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

   public synchronized boolean V(String var1) {
      if (var1 != null && var1.equals(this.nc)) {
         JsonObject var2 = new JsonObject();
         var2.addProperty(
            (String)com.yiyiaddon.m.b.a<"s1tlkopfuofzu6","RjDqw/jKAXWnAv7zPUelE6C82yByv1+BCTaZ4ahQdJ/UxA==",1102640547224380859,3207734845838023046,1224861467030829823,-9209196745910838832>(),
            var1
         );

         for (d var6 : d.values()) {
            JsonArray var7 = new JsonArray();

            for (c.a var9 : this.T.getOrDefault(var6, List.of())) {
               JsonObject var10 = new JsonObject();
               var10.addProperty(
                  (String)com.yiyiaddon.m.b.a<"s3uawssjpe8aiy","z0U4DhLp5vT5RkzXMwUe4COuD9Qyc4z51tvI5yRp",-6895210920303827377,-849950857968556783,8614960931409984407,-3134827130101488895>(),
                  var9.aj()
               );
               var10.addProperty(
                  (String)com.yiyiaddon.m.b.a<"stz02qz6s6ztt","m5UkWDBcJfA0e+cmnRu81wL4OVMLL4q331POVsU5",5982697562945759647,5109405939166277955,-6953236412638900977,7855462427100016806>(),
                  var9.ak()
               );
               var10.addProperty(
                  (String)com.yiyiaddon.m.b.a<"soxz4j76ki0lp","RIhs3tQ4hdO+otT54tFQtU5OteApm0BsSqQMpIoT",8829118901759026247,-843597666067700585,-4132280556255594656,-9102924352574054651>(),
                  var9.al()
               );
               var10.addProperty(
                  (String)com.yiyiaddon.m.b.a<"s11ik0yrqk6sm4","giBdy1SIs8GloVU0rX0XUaqGi4kRYSJKEwwaioA7QLg=",3775911194565373452,1521744841500249463,7794813873523221928,-8834901073845285997>(),
                  var9.bU()
               );
               var10.addProperty(
                  (String)com.yiyiaddon.m.b.a<"s1tlkopfuofzu6","RjDqw/jKAXWnAv7zPUelE6C82yByv1+BCTaZ4ahQdJ/UxA==",1102640547224380859,3207734845838023046,1224861467030829823,-9209196745910838832>(),
                  var9.bT()
               );
               if (var9.du() != null) {
                  var10.addProperty(
                     (String)com.yiyiaddon.m.b.a<"s5l3uypqq4k03","5810wPygKo+LCzsFsBekew3eVi7CQmj41UnR991WqYQ=",-9035852674644016019,-7133081532407000569,-8485040907663306282,-2431754528871167527>(),
                     var9.du()
                  );
               }

               if (var9.dv() != null) {
                  var10.addProperty(
                     (String)com.yiyiaddon.m.b.a<"s1wjn27s435xb3","hGNiwEfHxIFd8ONddOQqPzOgBj6dgeopsr0JW1yjpO8=",-6842481457979677477,-6497038796078540926,-3552847298449528178,-2042219848254975578>(),
                     var9.dv()
                  );
               }

               if (var9.dw() != null) {
                  var10.addProperty(
                     (String)com.yiyiaddon.m.b.a<"s7a57ufabo9wh","EY2O0r7tEEczfZYEXeH8G8ngygKbEEQAgqh0TiaEtrE=",1605421783167793656,-5471482191854274864,2321733667021828518,-1583157743861254302>(),
                     var9.dw()
                  );
               }

               if (var9.dx() != null) {
                  var10.addProperty(
                     (String)com.yiyiaddon.m.b.a<"s2185psno29fi6","nfZqKIm8Jwh06NI+wHYMDJ9L0zxff+Sqbbqc+E3yaLE=",3598797754755453705,8096519058262123969,8377741118548500831,5496976853997650348>(),
                     var9.dx()
                  );
               }

               if (var9.a() != null) {
                  com.yiyiaddon.e.n.h.a var11 = var9.a();
                  JsonObject var12 = new JsonObject();
                  var12.addProperty(
                     (String)com.yiyiaddon.m.b.a<"s1tlkopfuofzu6","RjDqw/jKAXWnAv7zPUelE6C82yByv1+BCTaZ4ahQdJ/UxA==",1102640547224380859,3207734845838023046,1224861467030829823,-9209196745910838832>(),
                     var11.bT()
                  );
                  var12.addProperty(
                     (String)com.yiyiaddon.m.b.a<"sz5ubcg1bgqk","tEnpB3c9V7slaogfTreALQMlc+I6fwTEtmDnYbCxchwE7WD6",8191126706902487922,-5575527693779907475,-3933144021385274042,-101440289811665772>(),
                     var11.dn()
                  );
                  var12.addProperty(
                     (String)com.yiyiaddon.m.b.a<"s1f7wrv9b5paos","DAXwDTXWeDcBrvbxFgajBcyk7vrQayRMS0QwkIJNKhtWIrcnGS82UZ6Q",-3395169527031951460,-2911738444725683606,-8382717252391127920,-9057791078213946436>(),
                     var11.do()
                  );
                  var12.addProperty(
                     (String)com.yiyiaddon.m.b.a<"s2p6mm40lmvh01","8gXntTJx34A2ZnUXVBYmaH4N8x5++kn/rM1JeAg/YXFl1e1ngGnPyw==",-6819519111438480376,-5841716076367933432,-2489529516339083072,1017329615743517966>(),
                     var11.dp()
                  );
                  var12.addProperty(
                     (String)com.yiyiaddon.m.b.a<"sguywies3yozo","VBG45AiT+l/gQtYUsMw2nCPOiAOyeV3u9eIcqT2qMmHyGoUTY+3aJg==",-1927629981025957112,-6371518417189341443,-7544584957386803591,525728007524760406>(),
                     var11.dq()
                  );
                  if (var11.dr() != null) {
                     var12.addProperty(
                        (String)com.yiyiaddon.m.b.a<"s347o4b38wqgug","ImiPjAzBWnOqo3mCzhxxKitVqqVqZx0GmfgMsrEABdF71dALuYA38Q==",-345303340740467158,-1079241394957227227,-6793608859638231772,6122477730695129975>(),
                        var11.dr()
                     );
                  }

                  if (var11.ds() != null) {
                     var12.addProperty(
                        (String)com.yiyiaddon.m.b.a<"s28uhwykw9raud","YokFPWtC5FDkkBu6pHkUjz+ZFvs9ZVhysOuTZPf2qQvNm9YC",943643361046028692,2692206095287380611,477878588213302037,5134480979336673002>(),
                        var11.ds()
                     );
                  }

                  var10.add(
                     (String)com.yiyiaddon.m.b.a<"su0accepxxqg5","geGrCohH5eYjS/AE5v7zriiynfl7EQvCGMbulDklKmQoH8w+",6139912327982408586,-4291072484017904546,3899854401113239243,-5759525814566262873>(),
                     var12
                  );
               }

               var7.add(var10);
            }

            var2.add(var6.aM(), var7);
         }

         return com.yiyiaddon.j.a.a(this.b(var1), var2);
      } else {
         return false;
      }
   }

   public synchronized void b(d var1, c.a var2) {
      List var3 = this.T.computeIfAbsent(var1, var0 -> new ArrayList<>());
      var3.removeIf(var1x -> Objects.equals(var1x.bU(), var2.bU()));
      var3.add(var2);
   }

   public synchronized void a(d var1, BlockPos var2, String var3) {
      this.b(var1, new c.a(var2.getX(), var2.getY(), var2.getZ(), var3));
   }

   public synchronized void a(c.a var1) {
      List var2 = this.T.computeIfAbsent(d.SPRINKLER, var0 -> new ArrayList<>());
      int var3 = -1;

      for (int var4 = 0; var4 < var2.size(); var4++) {
         if (((c.a)var2.get(var4)).a().equals(var1.a()) && Objects.equals(((c.a)var2.get(var4)).bU(), var1.bU())) {
            var3 = var4;
            break;
         }
      }

      if (var3 >= 0) {
         var2.set(var3, var1);
      } else {
         var2.add(var1);
      }
   }

   public synchronized void c(d var1) {
      this.T.computeIfAbsent(var1, var0 -> new ArrayList<>()).removeIf(c.a::G);
   }

   public synchronized void q(BlockPos var1) {
      List var2 = this.T.getOrDefault(d.SPRINKLER, List.of());
      var2.removeIf(
         var1x -> {
            if (var1x.G()) {
               switch ((int)com.yiyiaddon.m.b.a<"s287rofxk1nsfl","mC6BYmhAgnOszNsSBc8BFN9Tk/PjIpUXgK3PdlFNAaM=",4401264240608162802,4175911952289452866,-6714515363701710398,1932487518602662302>()) {
                  case 1121467607:
                     if (var1x.a().equals(var1)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1g7r5fjvp73hq","QtEIaCvCzs5Q7RoR3V6/ejgBh94j1ck7BYMzjAJaa/c=",3722612397647351260,6436488168728455188,-3183425736202828967,2311306348205355600>()) {
                           case -1641371910:
                              switch ((int)com.yiyiaddon.m.b.a<"s796g817nzcyj","HYDbZE2TSWPFW6aCGOxzGBAdHSiUePnny8fYRErXj5w=",-6946868464329569365,1224875378414679725,-7756430166420532591,-5103786438357266066>()) {
                                 case 951988779:
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

            switch ((int)com.yiyiaddon.m.b.a<"s1vede7xfe3rv2","T8d+jtWlq+JpGMNuNYNvBNqvAeXGr8kdcNDbF5+JmUg=",-4557319233513910434,7287272812637560393,-4881743880007398964,-2096250724233532932>()) {
               case 1411477324:
                  return false;
               default:
                  throw null;
            }
         }
      );
   }

   public synchronized void b(c.a var1) {
      this.T
         .getOrDefault(d.SPRINKLER, List.of())
         .removeIf(
            var1x -> {
               if (var1x.a().equals(var1.a())) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1bszc4cpwmhwx","Hx7dIWNRyelx9Dw6rR24jLd5jRuUct7DhGK/XnRelqg=",-2821195272435952087,5439198070128470022,-2013410789735157195,3325461632966879700>()) {
                     case 129891317:
                        if (Objects.equals(var1x.bU(), var1.bU())) {
                           switch ((int)com.yiyiaddon.m.b.a<"szafin7kzwtll","DmGtpmqDvjjGiryddQ3Q+tBJl533JYDOeybOknvQOF8=",6679647845815580981,3905759316182169779,7149717017007213736,-4357396721887079512>()) {
                              case -540294225:
                                 switch ((int)com.yiyiaddon.m.b.a<"s2g8bs4bdqtaol","f3u7R/YdvTi+KSaxA2lXUeZj560S0mgjRtbeNQjgoPk=",7672691289763307998,7223828781713871260,4926663016922198209,8173165895444705573>()) {
                                    case 933858413:
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

               switch ((int)com.yiyiaddon.m.b.a<"s3itzrxmij9kq3","hXWejx1e3/u3Sk5dBt6ojvigjhJHWOKGgvdYevlFkqU=",-4951373161640075607,417674679943673570,-7458192916490522203,8545111136084062518>()) {
                  case 1853753064:
                     return false;
                  default:
                     throw null;
               }
            }
         );
   }

   public synchronized c.a a(d var1) {
      List var2 = this.T.get(var1);
      if (var2 == null) {
         return null;
      }

      for (c.a var4 : var2) {
         if (var4.G()) {
            return var4;
         }
      }

      return null;
   }

   public synchronized boolean c(d var1) {
      List var2 = this.T.get(var1);
      if (var2 == null) {
         return false;
      }

      for (c.a var4 : var2) {
         if (!var4.G()) {
            return true;
         }
      }

      return false;
   }

   public synchronized List<c.a> a(d var1) {
      ArrayList var2 = new ArrayList();

      for (c.a var4 : this.T.getOrDefault(var1, List.of())) {
         if (var4.G()) {
            var2.add(var4);
         }
      }

      return List.copyOf(var2);
   }

   public synchronized List<c.a> b(d var1) {
      return List.copyOf(this.T.getOrDefault(var1, List.of()));
   }

   public synchronized int a(d var1) {
      return this.T.getOrDefault(var1, List.of()).size();
   }

   public synchronized void t() {
      for (d var4 : d.values()) {
         this.T.put(var4, new ArrayList<>());
      }
   }

   public synchronized void cy() {
      this.t();
      this.nc = null;
   }

   public static boolean W(String var0) {
      if (!(String)com.yiyiaddon.m.b.a<"s10ppfx5ter4in","5QLkA0+zipR5aQKehOHFLOoVyTlJRcOmHqiu/RFJMS4Biay9mvjM9Ai109SJmfql1I4MkK4d",9090194446127885173,-1103993610812969468,-1520487555124683495,157809591551954280>()
         .equals(var0)) {
         label22:
         switch ((int)com.yiyiaddon.m.b.a<"s6l76cij2c91z","MD7mxzIzTHFTNsNKOjISZ1dfjbXy+j5ovntMZKbF51w=",-7683581416509224503,-6775765437007004804,1493610302712413466,996285952567725259>()) {
            case 649902374:
               if (!(String)com.yiyiaddon.m.b.a<"s12z3gqsdyf81n","TY0CIX4kanG5++xy3/u7tUfarczRA3iN3iPZnhrco4Kp03A1i2hSdLSZ0UY61dFS0YhM08Pn",3776283258821095223,-219869139019105146,9181283107715421901,-6883854710599486894>()
                  .equals(var0)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2gfuh2xj4rqri","FEqSCi1LCkhJsyzEBzdS4hr1MvDJQo+hvWI82Y1B62M=",-1958966965250977892,-9078557077131084310,-4233275425385820334,-1457083332238642778>()) {
                     case 34287775:
                        return false;
                     default:
                        throw null;
                  }
               }

               switch ((int)com.yiyiaddon.m.b.a<"sahpbdncfb1dx","bFLw5xFgyJ3w1/e/+7acvg43GbsgyB9uxQrKYxyKBos=",-628406548067310172,7994222099303007335,-8779102050684666054,-8248329513484323857>()) {
                  case -608425683:
                     break label22;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      switch ((int)com.yiyiaddon.m.b.a<"s3r6mmonzne8x5","XslSe7sLeruAUH3pF0W5fywE+Hfnuh1OTEIF8g85XWA=",1075518112365097506,-8032083601266591138,4839394008637670973,1688704183932475281>()) {
         case -1085174797:
            return true;
         default:
            throw null;
      }
   }

   public String a(d var1, c.a var2, p var3) {
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1uswuewpeess4","m5HEbrN8Z0lAB6G+88bDfonl9N3beuedNWJW2SP+mRM=",-2718276204595717120,4766281466593122131,-3004453923348714936,2430551633488640876>()) {
            case 371372690:
               return (String)com.yiyiaddon.m.b.a<"s16x2pf4zmtpa7","RdTdTJ66Zx6SUWokGhVLTG2VtsHHNEpdyowRog4ldSwimQ==",3275095640020959140,1162706731776010221,-3291384577096352795,8601540336388001296>();
            default:
               throw null;
         }
      } else if (com.yiyiaddon.i.c.fp()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3mhzhza8648pb","FksjtKsNNSchldw3anRZ5kSc+DNKtabXGUdpv1PQf7Q=",-3798397357162018684,4662598722913285069,1924158013457732445,-8739007085441213174>()) {
            case -1301482831:
               if (com.yiyiaddon.k.e.c.fr()) {
                  if (Objects.equals(var2.bT(), com.yiyiaddon.e.n.a.bT())) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1vrufs814i1su","od4CNrfTHAssFLcFtQhJ0MsZ3MluphjIAHExEdFqv2Y=",3711338219312144572,-1769436662504881528,-1188956449974883675,2717417740186519433>()) {
                        case 1147134281:
                           if (Objects.equals(this.nc, com.yiyiaddon.e.n.a.bT())) {
                              if (!var2.G()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1cvuv9827gti0","tUOufQYjGKJPwNHxkOtcxOpZTwWMOkWxc0e+j+uFHsY=",5552986162289542471,-9027745739690123187,2053741070401951574,-4152715045006102383>()) {
                                    case -1094270689:
                                       return (String)com.yiyiaddon.m.b.a<"s11w97r65o8bcc","VgEUK6QwaowObKevoV3o44n1UWGm2Dl1Kwgq6xVhqpv0wlWIfZE3sAzMZTU=",-4782570903283334448,-7899426581753848724,5808625408258231213,-1349891817013175870>();
                                    default:
                                       throw null;
                                 }
                              }

                              Minecraft var4 = Minecraft.getInstance();
                              if (var4.level != null) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2tz0vhesrm26y","ByD3NESAtWGJkRibwqpNW43Y+Wkyyk/MBUGxu8iyB1U=",576979600421468422,-5621436243909443283,-5692163409597461313,-6886172735050936389>()) {
                                    case -1346998220:
                                       if (var4.level.isLoaded(var2.a())) {
                                          if (var1 == d.WATER_SOURCE) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s1mbhomz2rt7lc","3LX1wyJhY+Zi/yQ1d8KErCLwnYin3xkUTt8kQMhVHnc=",-6103014148316252894,3232721777125386270,2973679915558554226,-3956253825368619494>()) {
                                                case -1059747579:
                                                   return f(var2.a());
                                                default:
                                                   throw null;
                                             }
                                          }

                                          if (var1.H()) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s2lkrzw3awudiw","97GBaO5wz7t9FX+lJT2c5ypdFWEsy/pdrl8/v2ft92A=",-4085304886819824144,2220386803025416505,6953306761681178131,-8250223361791315536>()) {
                                                case -795127370:
                                                   BlockState var5 = var4.level.getBlockState(var2.a());
                                                   if (var5.isAir()) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s39qfmnm6asfvs","YY8Ij8YVPe6JM/xL80fqZdMftymFzoeiYh7F+Qgap9M=",3059113298580212379,6384239475053408469,4385849154865650101,-5114936425877505145>()) {
                                                         case 1299785800:
                                                            return (String)com.yiyiaddon.m.b.a<"s258gkyp96t407","2McezmesJuNRqUVdTiI2uuw3RIuuDQA56e307nx/Q4iAP7PSq7Ds5pD0",-6086682475865238134,-6291538230644689735,192050624663994180,2880229640808668727>();
                                                         default:
                                                            throw null;
                                                      }
                                                   }

                                                   if (!(var4.level.getBlockEntity(var2.a()) instanceof Container)) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s3nxswziv3is1p","ALFu8IlQrI8x9QInmVhusKy5HczN8ATv8JLK3Uo30/w=",6572192249782401939,3578760486267619720,5161838485796818862,7045881305281310081>()) {
                                                         case -2049909714:
                                                            if (var5.hasBlockEntity()) {
                                                               switch ((int)com.yiyiaddon.m.b.a<"s3iqskvz1q4179","bs6NIszJYvnT5bKdHxAJTrw3SQFa66V5B47NYGGvGMk=",-2835526771170470835,615306774561219288,8761016388719575794,-6902962189741902450>()) {
                                                                  case 1135018084:
                                                                     String var10000 = (String)com.yiyiaddon.m.b.a<"s12z3gqsdyf81n","TY0CIX4kanG5++xy3/u7tUfarczRA3iN3iPZnhrco4Kp03A1i2hSdLSZ0UY61dFS0YhM08Pn",3776283258821095223,-219869139019105146,9181283107715421901,-6883854710599486894>();
                                                                     switch ((int)com.yiyiaddon.m.b.a<"s2i876f6urbnti","aZ32UdxZFBee/kLArvvBZ6b3P7yFEQpfkgJuHptaS9Y=",7536208658850713438,3834347981512167067,7114933151729951754,8088481378785831813>()) {
                                                                        case 1124615329:
                                                                           return var10000;
                                                                        default:
                                                                           throw null;
                                                                     }
                                                                  default:
                                                                     throw null;
                                                               }
                                                            } else {
                                                               String var8 = (String)com.yiyiaddon.m.b.a<"s1beblml0gr8cl","TGX9ezF2yL/hzPX5Dic9yPhCxH1JZ/nhZDz7RCjfZaFJpn6m8Zt/h9CUb967AMaGZz/IvRqG",-1446557994356050141,-2325310855226097930,852506810042229382,6625929929856536990>();
                                                               switch ((int)com.yiyiaddon.m.b.a<"s2lohuyhjj212r","VhR7kUpM2Gb8fWiOO+DXLsmcHhtWtLQQgoUoyBNQWsY=",-1482714825160256775,-1054773709797977138,5537259493473481925,-6634450915787378686>()) {
                                                                  case -602402859:
                                                                     return var8;
                                                                  default:
                                                                     throw null;
                                                               }
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

                                          if (var1 == d.SPRINKLER) {
                                             label157:
                                             switch ((int)com.yiyiaddon.m.b.a<"s235fs90r9zkdq","yd5h0CxTQ2uDfWC6XzxltwUPFHYFoU+pw1gig/3DVPg=",7522608364368253145,-9168203406614687619,-9181056711508748891,-7720225733758063163>()) {
                                                case 1646677272:
                                                   com.yiyiaddon.e.n.h.a var7 = var2.a();
                                                   if (var7 != null) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s1w5th30l9m0qo","DyTcd7ooes/bNsAHUp0pl76XEbhF+hhYm+CQ5y1iq2A=",4996821545612774323,-4595967716443515564,-8097889529398610167,-6654850332230873014>()) {
                                                         case -1747161834:
                                                            if (!Objects.equals(var7.bT(), com.yiyiaddon.e.n.a.bT())) {
                                                               switch ((int)com.yiyiaddon.m.b.a<"s3jt2xzr3uael4","TlcfhbpzCINISrSVXE8jqMvqgJRHAacmfIx+JguvVfI=",-2641336136830957358,5424681367244734767,1766709242320888005,-800185820976998974>()) {
                                                                  case 1486370209:
                                                                     return (String)com.yiyiaddon.m.b.a<"s31ejalhkc2sm6","cJlMlWr0V3GfmqQ0dqYjpk8VSIqAnP4Z5TCSoRhtyzVasXc+KLiZ9KpUky1DSD/unXWItw==",-6555782287772562706,5490055917781862640,642593853024547694,1004665869671394353>();
                                                                  default:
                                                                     throw null;
                                                               }
                                                            }

                                                            if (!Objects.equals(var7.dn(), com.yiyiaddon.k.e.c.dn())) {
                                                               switch ((int)com.yiyiaddon.m.b.a<"sowymcmo4jmsg","Y1eTGQJn1IG1Ts7cj/ubUsgPc81qy5P28yiMhxxhXEs=",-302995548787220346,-8599701541593647648,-1499092124382462097,2439108643102537594>()) {
                                                                  case -409547163:
                                                                     return (String)com.yiyiaddon.m.b.a<"s1qqinkzcafjez","v7zZOV8tKim9wjo8mCi0CGR3jKNfK1Q4tDac4Hc4fGNvOENj8wJsMVI4hQlYXBPRslYw6i0LLAcEWtZUOzA+Bw==",-8603457401387384030,-4428926842102266396,-5486729658711093234,7975982990415989844>();
                                                                  default:
                                                                     throw null;
                                                               }
                                                            }

                                                            if (!Objects.equals(var7.do(), var2.dv())) {
                                                               switch ((int)com.yiyiaddon.m.b.a<"s23neihphe8pwu","rlUQdgjxqS4rlGdmjAIeWdTuUzFiSf67XMzstXCUEtY=",5911675478029511764,8470043509236356171,4520673158888246593,5900581208458748207>()) {
                                                                  case -935614225:
                                                                     return (String)com.yiyiaddon.m.b.a<"s19pnkhna4cs57","/4Y77whs+/gs7opop9RgClzjFyg4uC539vsyRsO1rhpldt5Q7t+V3Zj2KyYpNQsFvO6hMh0GiS52JQ==",5040230198004088757,-8275917086361129012,8444189205813669521,-2520646633995610199>();
                                                                  default:
                                                                     throw null;
                                                               }
                                                            }

                                                            if (!(var3.a(var7.do()) instanceof f)) {
                                                               switch ((int)com.yiyiaddon.m.b.a<"s37h18i5jwtjw9","r8Xtl4Y3ifmpP28OltGkOV0M92BOzdJR3NzKZ1tEog4=",-8998344670666116160,6272405282583550764,8667962648129447971,8365362315192563003>()) {
                                                                  case 1646814837:
                                                                     return (String)com.yiyiaddon.m.b.a<"s3re1zxhfn4sav","3QK6xqTTAhq3R/H6MCA0IQ7u+KU9xm7so9iZL5aZSdv4H8D+0EUfCGMTGWUQc6pXUNPwH8CSZQo=",6029819332526471740,-7753191193866740157,8078484109068217057,-8985051683815832217>();
                                                                  default:
                                                                     throw null;
                                                               }
                                                            }

                                                            if (!var7.x(var2.a())) {
                                                               switch ((int)com.yiyiaddon.m.b.a<"s8ibkzbc32609","7LkZ6+8AL/AfymhvXcIdU6jbg7erVBtgQ0J/PojSWKI=",-6020819104903226955,8853839133970886259,-5137660310599798073,-6750705488507955261>()) {
                                                                  case -1123847910:
                                                                     return (String)com.yiyiaddon.m.b.a<"sw3a7dbpkohr0","CYl21w8OYPr7TqjPcP8rtje5+l+Z0M1/0ZFeUuPnAs2voRqUpIfbWhgXSvtU5CIrpgV8ZAbGotPmTNZ7kBYjvQ==",328095259638802553,7983616370759341202,3764985658260205572,2576886128082147483>();
                                                                  default:
                                                                     throw null;
                                                               }
                                                            }

                                                            return null;
                                                         default:
                                                            throw null;
                                                      }
                                                   }

                                                   f var6 = a(var2.a(), var3);
                                                   if (var6 == null) {
                                                      return (String)com.yiyiaddon.m.b.a<"s3uxyblxcc31rm","Qkgpw19turMHLKIK0wVSIOvxu1ZEPzvRPl1qhh8WHy5vSKdZXgnZXYllf5+faTgq/JqYKASXDNR+ZmXR",6129572872624393983,-7757537759276493850,-310074646223770182,9021958823091234340>();
                                                   }

                                                   switch ((int)com.yiyiaddon.m.b.a<"s129s6gutvbvws","ROa0hGWxPYeShhF3YVbhle3xafegBBtzlYYL29/xVb4=",5535254508530216672,1297575579641512375,6976338735759150052,9048714746898106753>()) {
                                                      case 2105386860:
                                                         if (!var6.L().equals(var2.dv())) {
                                                            switch ((int)com.yiyiaddon.m.b.a<"s2ijitzt6jufo8","AXSrGZfnIa8ANAg/hyt7I1cCjTNAus7YXYxv0wlnC88=",8228487991062070080,5831727033266580726,3808545349702896813,7679940080573529522>()) {
                                                               case 1659576782:
                                                                  return (String)com.yiyiaddon.m.b.a<"s3uxyblxcc31rm","Qkgpw19turMHLKIK0wVSIOvxu1ZEPzvRPl1qhh8WHy5vSKdZXgnZXYllf5+faTgq/JqYKASXDNR+ZmXR",6129572872624393983,-7757537759276493850,-310074646223770182,9021958823091234340>();
                                                               default:
                                                                  throw null;
                                                            }
                                                         }
                                                         break label157;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          }

                                          if (var4.level.getBlockState(var2.a()).isAir()) {
                                             switch ((int)com.yiyiaddon.m.b.a<"sdze93ayws6ia","nEY0m4S++SzO7nMgngIL3UE/baN9WdxjpePtqzU0HL8=",5078899451054187219,-1186667924016696618,5343410011697688684,7712628050908785421>()) {
                                                case -590976187:
                                                   return (String)com.yiyiaddon.m.b.a<"s258gkyp96t407","2McezmesJuNRqUVdTiI2uuw3RIuuDQA56e307nx/Q4iAP7PSq7Ds5pD0",-6086682475865238134,-6291538230644689735,192050624663994180,2880229640808668727>();
                                                default:
                                                   throw null;
                                             }
                                          }

                                          return null;
                                       }

                                       switch ((int)com.yiyiaddon.m.b.a<"s3vian1mj11kqm","qq6ZljLvEcSRxE8ry3aaXBfD3zZhxrConXMrQXYgXdg=",1113954337718345584,9186163092424644981,2382364473976108279,8435263221109723171>()) {
                                          case 1522174484:
                                             return (String)com.yiyiaddon.m.b.a<"s10ppfx5ter4in","5QLkA0+zipR5aQKehOHFLOoVyTlJRcOmHqiu/RFJMS4Biay9mvjM9Ai109SJmfql1I4MkK4d",9090194446127885173,-1103993610812969468,-1520487555124683495,157809591551954280>();
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              return (String)com.yiyiaddon.m.b.a<"s10ppfx5ter4in","5QLkA0+zipR5aQKehOHFLOoVyTlJRcOmHqiu/RFJMS4Biay9mvjM9Ai109SJmfql1I4MkK4d",9090194446127885173,-1103993610812969468,-1520487555124683495,157809591551954280>();
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s1zbvag7mdt1y6","FQ2JnOumzlqiEI0Fav+EipKLvngJ3uj9D9+J2kw2uro=",-1643542735728706001,-4423300307738937679,-2957759594234689808,1147670632256119468>()) {
                              case 62574607:
                                 return (String)com.yiyiaddon.m.b.a<"s1iimkcp5dj02c","KjiAHI/iz0vEllf/t0wsO03fbCD7TQ4z4oyP1xZ5vJoWCnLjpAPJD7vyXvYg7g==",-3970729815588151966,1463834652590302479,-1219344237289602658,-883883057308993277>();
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return (String)com.yiyiaddon.m.b.a<"s1iimkcp5dj02c","KjiAHI/iz0vEllf/t0wsO03fbCD7TQ4z4oyP1xZ5vJoWCnLjpAPJD7vyXvYg7g==",-3970729815588151966,1463834652590302479,-1219344237289602658,-883883057308993277>();
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"sqylu039x48as","kEf9/H/DlmNDF8Zyi0jOd3c324noEZKTaQo3qZ7yJCY=",6253278646150617947,4458311027832748101,2371505648084230100,1977505322957176275>()) {
                     case -274447118:
                        return (String)com.yiyiaddon.m.b.a<"s2bke1nuul53ii","pZQaLzdANPVFiKb24BwSELiOscxWaPwMvxUGibDFAi56ZmTghd0C6GZokiK61Y2j",1039052778056253071,122338888645595825,106641053165250154,-5775316146391727821>();
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return (String)com.yiyiaddon.m.b.a<"s2bke1nuul53ii","pZQaLzdANPVFiKb24BwSELiOscxWaPwMvxUGibDFAi56ZmTghd0C6GZokiK61Y2j",1039052778056253071,122338888645595825,106641053165250154,-5775316146391727821>();
      }
   }

   public static String f(BlockPos var0) {
      ClientLevel var1 = Minecraft.getInstance().level;
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s21vp7fuzmqiny","S8ac2ldA/IvTonElGaYsG9F5G/fcLXLy6ZUEPXDZT0M=",853076554582167578,1530086508736798400,5118867013949606329,-8722225450962269505>()) {
            case 931224207:
               if (var1.isLoaded(var0)) {
                  FluidState var2 = var1.getBlockState(var0).getFluidState();
                  if (!var2.is(FluidTags.WATER)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1kv9alymc39j0","8LIlstf7WS0wVs/zVb9ecrLy4mYjLjHIIsqCXxTNcGo=",-8607552394357353616,8273162941317543473,258409651938997871,7103728506270059952>()) {
                        case 645994410:
                           return (String)com.yiyiaddon.m.b.a<"s21eia0jza94u1","OM+TipG7b6WqCZQP95rG0wTt4RkyQPJafX1siSHkovCB+3DB+uX41Zgb1eXsrI1d60o1YL7w8gZVLx7GrdKK6zLRH1w=",2591145200186071457,6903107274331892258,8607489559274681579,4629516109891168179>();
                        default:
                           throw null;
                     }
                  } else if (var2.isSource()) {
                     switch ((int)com.yiyiaddon.m.b.a<"sl0bx7bcoogwt","4jokS3pqI7fvQ/Gg/0yGc67c5pM5l8xZj8X3z5i8d0E=",2518964604090412899,4502555551133173566,-8994548363202642477,-5652746988159351934>()) {
                        case 301552059:
                           switch ((int)com.yiyiaddon.m.b.a<"s1ry7cxznty0zc","MLuLpNn2/KLjFKGqrCNS+gAeJ9KUTgu2qXvGkK2Q69Q=",1960579142219386493,6118302584836105679,6970544549868129120,3835020905809300333>()) {
                              case 1541690575:
                                 return null;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     String var10000 = (String)com.yiyiaddon.m.b.a<"s1uxfynyz7f33q","LN43YH6kmU31I9nmDDXjSMpU4zCKnHuQrha8DijQXUFyRTdsydq80NR+l97goodz6Y7Dayic",8694141417577600014,-397371171759172671,2527425591159014413,5714843571211241019>();
                     switch ((int)com.yiyiaddon.m.b.a<"s1wjzaquxhx91r","4ybGWQVn6txlE+qnNCtpyMWL+NRZtoevDvLRgG9nn+8=",-3101340516055994893,3904064589817265527,6271688569204163105,-133176413035524606>()) {
                        case 1921568368:
                           return var10000;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2bqi7x62pgjiy","AsAz7dgXm3ZkOLR3+U427+xFU1FomTEGBNb0YJvKJx4=",-6575966005279118686,6731459977820981343,3363751689375608851,-6502909203832003919>()) {
                     case -1197409414:
                        return (String)com.yiyiaddon.m.b.a<"s10ppfx5ter4in","5QLkA0+zipR5aQKehOHFLOoVyTlJRcOmHqiu/RFJMS4Biay9mvjM9Ai109SJmfql1I4MkK4d",9090194446127885173,-1103993610812969468,-1520487555124683495,157809591551954280>();
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return (String)com.yiyiaddon.m.b.a<"s10ppfx5ter4in","5QLkA0+zipR5aQKehOHFLOoVyTlJRcOmHqiu/RFJMS4Biay9mvjM9Ai109SJmfql1I4MkK4d",9090194446127885173,-1103993610812969468,-1520487555124683495,157809591551954280>();
      }
   }

   public static f a(BlockPos var0, p var1) {
      ClientLevel var2 = Minecraft.getInstance().level;
      if (var2 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s13quhmciubyov","JqY9U268V010hRwL6elEqcRnrYsBVOVeMiXtJX3MGpU=",-1561214778078217300,-123195573203564645,-331928979953077088,9007993049568557314>()) {
            case 2128643986:
               if (com.yiyiaddon.k.e.c.fr()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3fh8ix5z801c5","l4I8cN/C9nyebeLSixxj064gf9hymniO9LcW3dTwIVQ=",-1653018944391502896,2026342613894597185,-392790675368347433,-3035826755394657412>()) {
                     case -1713008547:
                        if (com.yiyiaddon.i.c.fp()) {
                           switch ((int)com.yiyiaddon.m.b.a<"sk4me131jw22f","0UwUvFAK9Hrg82F1dv5e/n9rDW5S/fe7y881Y8VvhEo=",-8368364047750173676,-351785335595828822,376787566563014188,6754061438090782059>()) {
                              case -810479061:
                                 if (var2.isLoaded(var0)) {
                                    switch ((int)com.yiyiaddon.m.b.a<"svpb71eyvgd1m","9HEbVYzm3xBuBK4QFnjrKk3kjmHdAvluonZBymygf7s=",-2444044434078047475,358085911504659767,7740208410415856973,5509495600551392725>()) {
                                       case -787402393:
                                          if (var1 != null) {
                                             com.yiyiaddon.g.d.a var3 = com.yiyiaddon.i.e.a.b(var2.getBlockState(var0));
                                             if (!(String)com.yiyiaddon.m.b.a<"s3ob4mb8tdxok9","UHDo0op0kVdGIpO08Ykepsqnwz4hUnUs02gFRDZqDhlaPQ==",-8444504792143993113,-215552057793665631,-3885502723202066730,-2923604009462254209>()
                                                .equals(var3.ge())) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s2y7rigz6dt4uj","y08/g4GpSaaLBAYswp38XTyCFX0xZKyxamJooseEBpw=",7470639795651952115,114638582069581957,5661963024509893442,-3234246312699723142>()) {
                                                   case -1155876716:
                                                      return null;
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             f var4 = null;
                                             Iterator var5 = var1.a(com.yiyiaddon.e.n.o.d.SPRINKLER).iterator();
                                             switch ((int)com.yiyiaddon.m.b.a<"sztx0b0pc1r7v","klWVx/yqOBWYqIHit8654peHIHfQyKcohIfMF5WydI0=",5899169240056210285,1812300573606948585,-8786885271919158877,575972346398494450>()) {
                                                case 1592597333:
                                                   while (var5.hasNext()) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s3kkoj060vvx2v","W/tr+OAdAb2rVKfJyyAycTltFr92+3I00rqd7UnmxwM=",-6510134916150374328,-7734255677035814971,4572149504865071195,-3362997374093533043>()) {
                                                         case 1465031215:
                                                            s var6 = (s)var5.next();
                                                            if (var6 instanceof f) {
                                                               switch ((int)com.yiyiaddon.m.b.a<"s3dehz0yw6jvmk","nX4kwkYQVbeKxt0m1T7iYlhYcR0ax0xDi4sxS3e26ZY=",6659729649778369485,-3805949392418495355,6693133879818198082,7270669974117520814>()) {
                                                                  case -455333449:
                                                                     label132: {
                                                                        f var7;
                                                                        label133: {
                                                                           var7 = (f)var6;
                                                                           if (var7.dJ() != null) {
                                                                              label75:
                                                                              switch ((int)com.yiyiaddon.m.b.a<"skarrai1scmrb","K5dMHosDHPRydHL/k1zmuDylBDjtZMcJa0xs71uVEwg=",5013914130203389158,1189582307531486303,-2958617130262555364,-2108848478282993409>()) {
                                                                                 case 1214815997:
                                                                                    if (var7.dJ().equals(var3.ea())) {
                                                                                       break label133;
                                                                                    }

                                                                                    switch ((int)com.yiyiaddon.m.b.a<"s1he9fqxup9opi","of4qpLnVJt7nlm+Gj+c+9uUJXr8UtBJMODXaPnotNx0=",-2362200511873780371,-901539972519132760,-4325598815175367433,-1694250073594928747>()) {
                                                                                       case -2074928815:
                                                                                          break label75;
                                                                                       default:
                                                                                          throw null;
                                                                                    }
                                                                                 default:
                                                                                    throw null;
                                                                              }
                                                                           }

                                                                           if (var7.dF() == null) {
                                                                              break label132;
                                                                           }

                                                                           label66:
                                                                           switch ((int)com.yiyiaddon.m.b.a<"s1zhwdsaxoiteq","i35M8hOl94vAY/YVxqdcuHkEv7xA4TqWCKJyIyWak4Q=",-6801509456158336960,-1472968669791211019,-428377920215257167,-4201778727250022384>()) {
                                                                              case -1008369918:
                                                                                 if (!var7.dF().equals(var3.dv())) {
                                                                                    break label132;
                                                                                 }

                                                                                 switch ((int)com.yiyiaddon.m.b.a<"s12qk0f7szuga5","+dTjjuV0e+VIz5/8sKLzmPVcc/eC+yhY+qHsdudD7Bs=",6959532648848524703,5545641396098851586,-2805626091892572243,-233520580787783110>()) {
                                                                                    case 1727195635:
                                                                                       break label66;
                                                                                    default:
                                                                                       throw null;
                                                                                 }
                                                                              default:
                                                                                 throw null;
                                                                           }
                                                                        }

                                                                        if (var4 != null) {
                                                                           switch ((int)com.yiyiaddon.m.b.a<"s2shcxwe1976sb","UGU4N26Rs84zIKGJkH3qaEoobl8xUaO3vYCwjyjG91A=",-4766920410099473586,7612592471701223192,-2077746300484378745,-1066559937772343019>()) {
                                                                              case 123182024:
                                                                                 if (!var4.L().equals(var7.L())) {
                                                                                    switch ((int)com.yiyiaddon.m.b.a<"s3iz304tutikt9","sJHuQ9x7hbOFtBBm01MX/hZ/nEefDuyHhSZ2qRLJ07Q=",-6606465476242433843,7150874624841833752,988822082337511966,5119462679165012442>()) {
                                                                                       case 968417936:
                                                                                          return null;
                                                                                       default:
                                                                                          throw null;
                                                                                    }
                                                                                 }
                                                                                 break;
                                                                              default:
                                                                                 throw null;
                                                                           }
                                                                        }

                                                                        var4 = var7;
                                                                        switch ((int)com.yiyiaddon.m.b.a<"s3rufwvapzmz03","uI8UwfYxyEbk66o6bn/MngbCNf0NY/KDnR45F5zcEHU=",6355110827242430980,-2066200420674335579,-1026466739051240367,2194978672263501214>()) {
                                                                           case -324223367:
                                                                              break;
                                                                           default:
                                                                              throw null;
                                                                        }
                                                                     }

                                                                     switch ((int)com.yiyiaddon.m.b.a<"s2q8khlyujolqd","5uSSavgANGI3cPn+dU6kLEwvuPwnYSSuBP9Tj8ZgLbk=",-4256144221363062502,-4639864629109875049,260250236655194136,-3467969209014527326>()) {
                                                                        case 273266664:
                                                                           continue;
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

                                                   return var4;
                                                default:
                                                   throw null;
                                             }
                                          }

                                          switch ((int)com.yiyiaddon.m.b.a<"s3bbu12piouiqm","G/vz8RNISoJ8h/YKs+256rHVHJmAMmysNVflWv6lIfw=",-3647790839014955142,5096937959464826368,-8225744451840222965,3998172258147903978>()) {
                                             case -1497411403:
                                                return null;
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

      return null;
   }

   public static f a(BlockPos var0, p var1, Collection<String> var2) {
      ClientLevel var3 = Minecraft.getInstance().level;
      if (var3 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3bi3ng3ydsrrc","Hz5jN74us1htjq+O8SydYp+L7BOnU9eXOBw2MJHO840=",246273638679846330,-2398652290488429405,4021474941806643652,6791491521500264630>()) {
            case -725040269:
               if (com.yiyiaddon.k.e.c.fr()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3jycia15nxuev","6fORafUGziRL24N3318Q1gP/c+hJTdFqehaSkMOIh/A=",5557126549761072628,-2839810230945569823,2971835450375851682,-1333157625861182321>()) {
                     case -28263249:
                        if (com.yiyiaddon.i.c.fp()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1vnu1qcr43ws4","qtksewkwmVPqUl3ZZhgWSYevHNt4FO1Zcj4/+bWEv44=",2574319444382073593,-1278937997471346829,-5252513767739459446,-8939698201821053180>()) {
                              case 844714176:
                                 if (var3.isLoaded(var0)) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2qw3scjrj7xwf","uwSntpFxLca+MvNB3YWJ1TWproB4VbY6ygAIO5JkzUg=",-4738875032813300926,-5794332829236928790,8844435187149706087,-1833756546987574783>()) {
                                       case 1368331188:
                                          if (var1 != null) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s31uu1k235m1hw","3zXxcMw/GjjQvpQl5684+pbZfBBUjm3I68mezSknm60=",-2861025239725886215,-8629118803353826750,6062713084153568671,-3111143591782698179>()) {
                                                case 1247900649:
                                                   if (var2 != null) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s66z9hhg939i7","KKgYnp5wXI9qVWgA1NPcJSCdIx8qLI/NEkcHK1SN/c0=",-7584879619357671457,7383812018457096725,56894386182230876,5284291622760795186>()) {
                                                         case -575775475:
                                                            if (!var2.isEmpty()) {
                                                               com.yiyiaddon.g.d.a var4 = com.yiyiaddon.i.e.a.b(var3.getBlockState(var0));
                                                               ArrayList var5 = new ArrayList();
                                                               if (var4.ea() != null) {
                                                                  label116:
                                                                  switch ((int)com.yiyiaddon.m.b.a<"siof02of9hlgo","Q81yloqBLoGmdlWMtn5GqGfRewL68ELYwadZpi9i97Q=",7292014119246923965,8976887797305956884,3055781139643543292,7496677241821638815>()) {
                                                                     case -167120166:
                                                                        String[] var6 = var4.ea()
                                                                           .split(
                                                                              (String)com.yiyiaddon.m.b.a<"s1gnb9y91afsdp","fw6wzqJ/dVkkjEn0ldJkBXhxlLa0vvEA3WsRd2UJonw=",741631017699920271,-7335766561139508286,-9078158521309627771,3704495709515522331>()
                                                                           );
                                                                        int var7 = var6.length;
                                                                        int var8 = 0;
                                                                        switch ((int)com.yiyiaddon.m.b.a<"s1be89vuqmodj2","q1AQIUw/MXDuk9xdZmPSqlcPHMiQDbuqtcNnBn4ptzU=",731945625905568208,1072114835115214165,-8365584576390494850,7755271855958451622>()) {
                                                                           case -1584931741:
                                                                              while (var8 < var7) {
                                                                                 switch ((int)com.yiyiaddon.m.b.a<"s6uaxto4o7q1v","37XHDJMo1etDonDk05rwl2sWgJMxXlDtuDTDQzc3KgE=",6548283586226507721,-8772170593486462563,-6721202097307544867,5345505707912005155>()) {
                                                                                    case -1695620497:
                                                                                       String var9 = var6[var8];
                                                                                       var5.add(var9.trim());
                                                                                       var8++;
                                                                                       switch ((int)com.yiyiaddon.m.b.a<"s8k3cucwj7nkw","K1u8/oW2CGRX/N5dcFNpjTsk4tdWWQnkGP5E7/Ba+WY=",4772790951587755485,-1934684815458764981,2848467712256773514,-8879840406655344784>()) {
                                                                                          case 1849551449:
                                                                                             continue;
                                                                                          default:
                                                                                             throw null;
                                                                                       }
                                                                                    default:
                                                                                       throw null;
                                                                                 }
                                                                              }
                                                                              break label116;
                                                                           default:
                                                                              throw null;
                                                                        }
                                                                     default:
                                                                        throw null;
                                                                  }
                                                               }

                                                               f var11 = null;
                                                               Iterator var12 = var2.iterator();
                                                               switch ((int)com.yiyiaddon.m.b.a<"s2w5yylpji2vm3","vc/4YdLqNLjyzMlzedykUnnW66IMKM96zUrZ+W4unDU=",3030155180148132857,9122798848181789159,-9167899253964342492,3606203826874038357>()) {
                                                                  case 344776609:
                                                                     while (var12.hasNext()) {
                                                                        switch ((int)com.yiyiaddon.m.b.a<"sndb8h8e55xnl","iFuPTz55y+lhRXIkbPe1o7novlvn/7Dwh9l4TSwF5L4=",-338657562329463203,2718019212599416508,-2646227950383613648,7766291956915205290>()) {
                                                                           case 1578672546:
                                                                              String var13 = (String)var12.next();
                                                                              s var10 = var1.a(var13);
                                                                              if (var10 instanceof f) {
                                                                                 switch ((int)com.yiyiaddon.m.b.a<"s8gjvarz6d9wv","NzpmwrAtOC6PV+9XYHXX0ZRVpeYh7dG+5CtDfMCo7X8=",-5026020396296672196,-1592725733277617043,-4970494834289978554,-345135721488559621>()) {
                                                                                    case 1228468120:
                                                                                       f var14;
                                                                                       boolean var10000;
                                                                                       label146: {
                                                                                          label145: {
                                                                                             label179: {
                                                                                                var14 = (f)var10;
                                                                                                if (var4.dv() != null) {
                                                                                                   label99:
                                                                                                   switch ((int)com.yiyiaddon.m.b.a<"s3jd03unw73jxz","vV6w814MP5q6Zvn32JmIgTfTaFVtQYcPbJ949i7vzTU=",1563182908883779480,8618151614803906549,-3241198010647913235,3570401665201139042>()) {
                                                                                                      case -1885931673:
                                                                                                         if (var4.dv().equals(var14.dF())) {
                                                                                                            break label179;
                                                                                                         }

                                                                                                         switch ((int)com.yiyiaddon.m.b.a<"s2h9wmia4g3qrr","eMUSABxB7JbamA2Hp4sVBzZjtkNxn15RU3jVtQWNbo0=",7032042325470719131,-6828774486669967283,-8905584585691052569,6749092855785746614>()) {
                                                                                                            case 1267976269:
                                                                                                               break label99;
                                                                                                            default:
                                                                                                               throw null;
                                                                                                         }
                                                                                                      default:
                                                                                                         throw null;
                                                                                                   }
                                                                                                }

                                                                                                if (var14.dJ() == null) {
                                                                                                   break label145;
                                                                                                }

                                                                                                label93:
                                                                                                switch ((int)com.yiyiaddon.m.b.a<"s3a1mgrnttred5","gKwD7YaPnB+oZEgZeAx58lwM0sLHICuRt4auMp4TucY=",4970867617713992123,-7173460814798583870,3531864907250783134,-31621624569052406>()) {
                                                                                                   case -1108599673:
                                                                                                      if (!var5.contains(var14.dJ())) {
                                                                                                         break label145;
                                                                                                      }

                                                                                                      switch ((int)com.yiyiaddon.m.b.a<"s2mnmp31ounbln","BUSrBr5+42wTj4tHd3GEIxb/BMiGXnBWfA5kCB4M5NA=",-6518914675809635288,-7015893803572111341,-8371967203001106956,4177605443889056319>()) {
                                                                                                         case 574830201:
                                                                                                            break label93;
                                                                                                         default:
                                                                                                            throw null;
                                                                                                      }
                                                                                                   default:
                                                                                                      throw null;
                                                                                                }
                                                                                             }

                                                                                             var10000 = true;
                                                                                             switch ((int)com.yiyiaddon.m.b.a<"s23vxtpo718ha9","F0XghJmoin2JbPH+6m1Uwh8G/TixVKJPNbnbPGxbFao=",-3475035226833049684,1564760723139391903,6995355303105200436,-9115587575184808983>()) {
                                                                                                case 873437093:
                                                                                                   break label146;
                                                                                                default:
                                                                                                   throw null;
                                                                                             }
                                                                                          }

                                                                                          var10000 = false;
                                                                                          switch ((int)com.yiyiaddon.m.b.a<"s2qa0xe0kjs1wl","iEfRULXuTxte86pXzr5J2BBKc6r4GYfNNbZb1PRDSjU=",8036895266244887519,-5086794724552332430,1312027897290157511,7490728457190932735>()) {
                                                                                             case 1102702039:
                                                                                                break;
                                                                                             default:
                                                                                                throw null;
                                                                                          }
                                                                                       }

                                                                                       boolean var15 = var10000;
                                                                                       if (!var15) {
                                                                                          switch ((int)com.yiyiaddon.m.b.a<"s3kdby9713wtk","oS6D8g7ZtX3ptsPT51bnRuy7F4RU1pF9cJNO2njZIFQ=",-3038680981442666769,6433879533597718765,-7390465112009466611,-5659362531992827091>()) {
                                                                                             case 589683439:
                                                                                                switch ((int)com.yiyiaddon.m.b.a<"s229b7dla9dhyk","1KUFo6F3YzR3oboSfHCYfMKSWhpIT9qLIW+Y7KOEqAE=",4379244209343632903,4789901691228127030,2883965489781004196,6420856901192484980>()) {
                                                                                                   case -1255756925:
                                                                                                      continue;
                                                                                                   default:
                                                                                                      throw null;
                                                                                                }
                                                                                             default:
                                                                                                throw null;
                                                                                          }
                                                                                       } else {
                                                                                          if (var11 != null) {
                                                                                             switch ((int)com.yiyiaddon.m.b.a<"s3ejj24bkmeyib","X6rFSKvPSwj8l/DIKSyP0tJwDrPqcpx7hGU56m4Y34A=",-4899129519162378450,-5027300495554702118,2125102935715671360,3349585516741553446>()) {
                                                                                                case 1466665306:
                                                                                                   if (!var11.L().equals(var14.L())) {
                                                                                                      switch ((int)com.yiyiaddon.m.b.a<"s1jqw8mfue14x0","+pg4+LH8uK2Onm9edgarXQWSERgXPKjZcDNBADm+otI=",2956103976659997008,-5508803467445787896,-636729858863841197,8715841115498449281>()) {
                                                                                                         case -670735256:
                                                                                                            return null;
                                                                                                         default:
                                                                                                            throw null;
                                                                                                      }
                                                                                                   }
                                                                                                   break;
                                                                                                default:
                                                                                                   throw null;
                                                                                             }
                                                                                          }

                                                                                          var11 = var14;
                                                                                          switch ((int)com.yiyiaddon.m.b.a<"s4uedayu3ivij","17tVnI/zovV1KU47xD+Ll8UudXv++aefHvXB0oOUKfw=",-2219590413106742421,4546804412235671041,7831337858054302863,7019101966380688029>()) {
                                                                                             case 405626831:
                                                                                                continue;
                                                                                             default:
                                                                                                throw null;
                                                                                          }
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

                                                                     return var11;
                                                                  default:
                                                                     throw null;
                                                               }
                                                            }

                                                            switch ((int)com.yiyiaddon.m.b.a<"s1vrdst49orqts","TYzIlngwHe1JzZm5MYLHiPCx5lfLf6aN12rdnpAjoZs=",-1486441339581606159,-4749647391088033846,1721023550200888405,3351621946040372377>()) {
                                                               case 1290239239:
                                                                  return null;
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

      return null;
   }

   public record a(int me, int mf, int mg, String rR, String rS, String rT, String rU, String rV, String rW, com.yiyiaddon.e.n.h.a a) {
      public a(int var1, int var2, int var3, String var4, String var5, String var6, String var7, String var8) {
         this(var1, var2, var3, var4, var5, var6, var7, var8, com.yiyiaddon.e.n.a.bT(), null);
      }

      public a(int var1, int var2, int var3, String var4, String var5, String var6, String var7, String var8, com.yiyiaddon.e.n.h.a var9) {
         this(var1, var2, var3, var4, var5, var6, var7, var8, com.yiyiaddon.e.n.a.bT(), var9);
      }

      public a(int var1, int var2, int var3, String var4) {
         this(var1, var2, var3, var4, null, null, null, null);
      }

      public BlockPos a() {
         return new BlockPos(this.me, this.mf, this.mg);
      }

      public boolean G() {
         if (this.rR != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s29y77gdiqdx91","2U2Hi7ibNgAN8mbvtbG4yPv/DH4qDBAbhw4bV6y5qD8=",2054764661865584861,-7524532463088725389,1616270119297229845,864968490578672200>()) {
               case -2142339393:
                  if (this.rR.equals(com.yiyiaddon.e.n.a.bU())) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3pt05ac8jqjvb","uP/TYpj9Xj32JSjIHpMARg7gy3c+gF00h5co44cS/gs=",7475647316287164609,-2063185679588271039,-8328432998666833487,7449146283328767948>()) {
                        case -898862451:
                           switch ((int)com.yiyiaddon.m.b.a<"s752rnti9hglz","OtUvtLILe2ZmoH3NTfzVSAbBSB1eRfyJkbo6fcfVavk=",748671559663801683,1165860736533973136,-2916037089868893729,-1870758588491150507>()) {
                              case 517424474:
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

         switch ((int)com.yiyiaddon.m.b.a<"s3824p7w75abpq","lARlbvZmvfUrFHRFiPEZVgQGVBoR3dWCdVXGPIQ5egA=",-8573786126351130651,-6937932630782279829,-8490736387583734590,-3134463960704073088>()) {
            case 1003849375:
               return false;
            default:
               throw null;
         }
      }

      public boolean dc() {
         if (this.rS != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s1v0ddac3j2jff","enbqbtK8iE9LRLkFgeemDk2c52Avo2icVpR8F9S/xFU=",-3429743657302492167,-731384475540853523,8437723870400780399,7280641308101724480>()) {
               case 412163218:
                  if (!this.rS.isBlank()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2flhn61pmt2fl","GAi3ekTCql8+MLQ+45h3NVtaZUb/zWahTPCyJSJFZ1E=",-129121641280051353,6399361824502556874,6913955172723213677,-2628243598130193849>()) {
                        case 981306599:
                           switch ((int)com.yiyiaddon.m.b.a<"s1znirfb4xar8w","Q2NDTQ/a/esP3J8vyvBesBRj0lZUx6NVzLAhHlnCiMQ=",9091914620517093909,-898572193208746071,-925864460531451426,-8443340007349894488>()) {
                              case 922709875:
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

         switch ((int)com.yiyiaddon.m.b.a<"s3bfvbya0hj2qy","rzDewCMH3WSMiee+lWeJYNeLjy4Jff1c6uSq8dHmjLs=",-3132821841258935101,8431346041260717413,8677576877363645382,3062923888853629049>()) {
            case -1888270517:
               return false;
            default:
               throw null;
         }
      }

      public int aj() {
         return this.me;
      }

      public int ak() {
         return this.mf;
      }

      public int al() {
         return this.mg;
      }

      public String bU() {
         return this.rR;
      }

      public String du() {
         return this.rS;
      }

      public String dv() {
         return this.rT;
      }

      public String dw() {
         return this.rU;
      }

      public String dx() {
         return this.rV;
      }

      public String bT() {
         return this.rW;
      }
   }
}
