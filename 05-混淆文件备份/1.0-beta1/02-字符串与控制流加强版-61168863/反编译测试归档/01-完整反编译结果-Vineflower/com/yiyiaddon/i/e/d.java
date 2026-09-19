package com.yiyiaddon.i.e;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.server.packs.resources.ResourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class d {
   private static final Logger s = LoggerFactory.getLogger(
      (String)com.yiyiaddon.m.b.a<"s48be6du8nx7t","vGldtB1yW7DoXG1bNAxxCJt8gpSyRPAyIFXtj/I4pBbEXudQxMh5yu7eGPQA8uM2MSSEZ0KEFMP6Z33trSxTICDqARWZ7Ff5",-2962166258261782767,5744443220741334811,-2757831015923982278,5926126737043967647>()
   );
   public static volatile boolean fr = false;

   private d() {
   }

   public static ResourceManager a() {
      return Minecraft.getInstance().getResourceManager();
   }

   public static Set<String> z() {
      ResourceManager var0 = a();
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1180qdf426mcn","rskDSy0j/a6rrYwd3imsaqsW0pPY3Ey3uUwemw5w/jE=",411897028422101245,-7661857226669849655,-3916707542632527184,8014686948185627428>()) {
            case 1746140298:
               Set var10000 = Set.of();
               switch ((int)com.yiyiaddon.m.b.a<"sabvuncwq95eh","VSVGcpZrSVOnOR+mcTn7UVhSe+ANCKKx2OLPgFVocpo=",-1906685864164055355,-2757265770157697532,-6752863048578307940,6999563821907609906>()) {
                  case -1133299353:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         Set var1 = var0.getNamespaces();
         switch ((int)com.yiyiaddon.m.b.a<"s34da263atu7vw","exJWVjDIgeI6BXU37Z0uUXsXcxVghZAl3n6308Nrvms=",-9112849897779760706,1554715902385968944,-1022759360000700121,-1400454474384749710>()) {
            case 2078701900:
               return var1;
            default:
               throw null;
         }
      }
   }

   public static List<PackResources> bC() {
      ResourceManager var0 = a();
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s28r49l6kyuj9h","pZcm+ZC4Wg4y2ZBfpd1xuoBVdxjw+Syq/tLXIyg+LWE=",7054324841809175504,-2197163124505505600,5452270367173198926,-6702010158729800903>()) {
            case -1640187153:
               return List.of();
            default:
               throw null;
         }
      } else {
         ArrayList var1 = new ArrayList<>(var0.listPacks().toList());
         Collections.reverse(var1);
         return var1;
      }
   }

   public static d.a a(Identifier var0) {
      if (var0 == null) {
         return d.a.a(
            (String)com.yiyiaddon.m.b.a<"s26au31lc6j1xf","8V/llr5iCAoNOvyM2X1UxJPVqVPSFS2zunFMjeuHbJh5399veXKEMg==",3592430467602985559,-1710412864761339695,5154941547886172858,9074681787004021662>()
         );
      }

      ResourceManager var1 = a();
      if (var1 == null) {
         bi(
            (String)com.yiyiaddon.m.b.a<"s56ef90fpnhc3","lf+yoYRHfjyLLopSVsvP3rdhEYoiX+HWbwj7Oc0Cwpvd+aIaPHdh5c6BkDXctqycGKzxY4liX43rwwaYvbXEy2aMmu+rezlyFQ2e/vgbxpfeSF92AOpMjIocc5k=",-6992148262025684164,5151544646970484588,4618019271977993300,-840766022132316067>()
         );
         return d.a.a(
            (String)com.yiyiaddon.m.b.a<"szk62evkx0jky","+fezpnse+9/FFDIhBgy57WYalBSO2lEjaJaJ7dkklFTbNh9JQBKYV4x1fvs=",-2025892814204227514,-5482653374399683782,-6572395405399073617,-7864382443484629096>()
         );
      }

      try {
         List var2 = bC();
         bi("" + var2.size() + var0);

         for (PackResources var4 : var2) {
            Serializable var5 = var4.packId();

            IoSupplier var6;
            try {
               var6 = var4.getResource(PackType.CLIENT_RESOURCES, var0);
            } catch (Exception var13) {
               bi(var5 + var13);
               continue;
            }

            if (var6 != null) {
               bi(var5 + "");

               try (InputStream var7 = (InputStream)var6.get()) {
                  JsonObject var8 = JsonParser.parseReader(new InputStreamReader(var7, StandardCharsets.UTF_8)).getAsJsonObject();
                  return d.a.a(var8);
               } catch (Exception var12) {
                  bi(var12 + "");
                  return d.a.a(
                     (String)com.yiyiaddon.m.b.a<"s30jh5yux68m04","IRb+a7L1C8lzNthv4vdstg7JcodlEwvD0AVPxHzB1dFUMd1Z0t8Ph6xxV2U=",-2296470902982565114,2090929128168745510,-6497373746593637408,-2413796390439870652>()
                  );
               }
            }

            bi(var5 + "");
         }

         bi(var0 + "");
         return d.a.a(
            (String)com.yiyiaddon.m.b.a<"s1i4uytc2mss3c","tGHe7jgsESZ49d7s84zP6JptIU7VMvV32PW6rnROTpQZy+F9bif0v4eW",5002855759726342123,8742923821790060768,2093201705316181305,-2260690713659184613>()
         );
      } catch (Exception var14) {
         bi(var14 + "");
         return d.a.a(
            (String)com.yiyiaddon.m.b.a<"s3hbe0kba55gvs","WgrlKg7C/O3frUzcpZ5farr+ONGRq0LZHkFaBjTYfZ4NC6Vu1k2doA==",3125097368961468417,-147553224231705430,7769997123860769515,-1941255111288841933>()
         );
      }
   }

   public static void bi(String var0) {
      if (fr) {
         switch ((int)com.yiyiaddon.m.b.a<"s3hsj34d517at6","iY+r9onfEoiwNMYQMsqI+e7K1wD3w6gRM7lmni06Z0Q=",-3683084024152839538,485706973086296391,3755862535579353563,110219271403582555>()) {
            case -1308777933:
               s.info(
                  (String)com.yiyiaddon.m.b.a<"s1ftriwkxtco2l","fYwcxPEjFtc4Th8YSxuCC3EgkcSa6BzDtS6gAR4sMWnKb8hP5IwYJ9fUm9tyKFMdk+yI/HOUCT23PLE2EbI=",6868327826000219927,4103263414561916308,-1129906328568967834,4676702374103740740>(),
                  var0
               );
               switch ((int)com.yiyiaddon.m.b.a<"s37apgm4dt3djg","4ezTNwho+r0nz91164wkj2a8D19l8mO0FVSjaWWzSk4=",5756867437259000200,-4836800578247693133,-7595142371177833274,-4613740501210074116>()) {
                  case 1192745719:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   public record a(JsonObject a, String Ei) {
      public boolean b() {
         if (this.a != null) {
            switch ((int)com.yiyiaddon.m.b.a<"st2zz0aytsxtq","s+UadgEIY5M9lpZc5i12XBxTflXU931E5OGIm9Gk/cI=",2793933300774330957,-3235012051681684833,-2506228340968469380,-1926097875578033770>()) {
               case -1088485035:
                  switch ((int)com.yiyiaddon.m.b.a<"s2ygh25f9uhtxo","k+xpjkIQUrwsJ65s9NdW6Mxkz/i1yLg0Y22X57JUPSs=",-6037575229354673010,3989077033473954805,-2119381857528611797,-8808406896093927385>()) {
                     case -594788478:
                        return true;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            switch ((int)com.yiyiaddon.m.b.a<"sr8n8hni1rrob","5a++0f/bRbeYFR8/WRxZ23AhtDsZ4JRXISEkbDppb+g=",8292385200465137683,-4005019690969580086,-8332033461354515848,842536057283558903>()) {
               case -467318948:
                  return false;
               default:
                  throw null;
            }
         }
      }

      static d.a a(JsonObject var0) {
         return new d.a(var0, null);
      }

      static d.a a(String var0) {
         return new d.a(null, var0);
      }

      public String br() {
         return this.Ei;
      }
   }
}
