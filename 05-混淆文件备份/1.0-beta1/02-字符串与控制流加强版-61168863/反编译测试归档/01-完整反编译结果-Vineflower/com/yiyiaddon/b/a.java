package com.yiyiaddon.b;

import com.mojang.blaze3d.opengl.GlRenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.GpuSampler;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.yiyiaddon.m.b;
import java.util.HashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.OverlayTexture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class a {
   private static final Logger e = LoggerFactory.getLogger(
      (String)com.yiyiaddon.m.b.a<"s12bhg9xe3n20g","Oc/EFWmC1Naf/+aPcdL0VYgHBr2rnKkx889YQwYRgOeQVr3euodwg1wu+xZEIAFJu5zkbIsCDHEHM39ucFS8nZay3RLXlJqVC5M=",-5058159219363121920,-7942005408366521368,-7546204029520927682,-413037566313573696>()
   );
   private static final String l = (String)com.yiyiaddon.m.b.a<"sd5gjdw4wy23u","kNVFYXboa9SsLdINOR1wkIt9qxq4aJIqQWROgSyrRNCcD573lbFPl825YVg=",-8842857389801956480,-6520300269087440890,-8229081202821274168,-8366133999808049674>();
   private static final String m = (String)com.yiyiaddon.m.b.a<"sgj3c33lcr5e7","w2hCnzZnrzhRilGHo4XTOfX3pPDKOjdysgQ49PWeANPbJWj5gRj9c5iM95k=",5000951507456107664,-8960405386323365807,-8808594496720127091,-1377935461960786894>();
   private static boolean b;
   private static boolean c;

   private a() {
   }

   public static void a(GlRenderPass var0) {
      if (!b) {
         try {
            HashMap var1 = var0.samplers;
            if (var1 == null) {
               return;
            }

            boolean var2 = !var1.containsKey(
               (String)com.yiyiaddon.m.b.a<"sd5gjdw4wy23u","kNVFYXboa9SsLdINOR1wkIt9qxq4aJIqQWROgSyrRNCcD573lbFPl825YVg=",-8842857389801956480,-6520300269087440890,-8229081202821274168,-8366133999808049674>()
            );
            boolean var3 = !var1.containsKey(
               (String)com.yiyiaddon.m.b.a<"sgj3c33lcr5e7","w2hCnzZnrzhRilGHo4XTOfX3pPDKOjdysgQ49PWeANPbJWj5gRj9c5iM95k=",5000951507456107664,-8960405386323365807,-8808594496720127091,-1377935461960786894>()
            );
            if (!var2 && !var3) {
               return;
            }

            Minecraft var4 = Minecraft.getInstance();
            if (var4 == null || var4.gameRenderer == null) {
               return;
            }

            GpuSampler var5 = RenderSystem.getSamplerCache().getClampToEdge(FilterMode.LINEAR);
            if (var2) {
               OverlayTexture var6 = var4.gameRenderer.overlayTexture();
               if (var6 != null) {
                  var0.bindTexture(
                     (String)com.yiyiaddon.m.b.a<"sd5gjdw4wy23u","kNVFYXboa9SsLdINOR1wkIt9qxq4aJIqQWROgSyrRNCcD573lbFPl825YVg=",-8842857389801956480,-6520300269087440890,-8229081202821274168,-8366133999808049674>(),
                     var6.getTextureView(),
                     var5
                  );
               }
            }

            if (var3) {
               GpuTextureView var8 = var4.gameRenderer.lightmap();
               if (var8 != null) {
                  var0.bindTexture(
                     (String)com.yiyiaddon.m.b.a<"sgj3c33lcr5e7","w2hCnzZnrzhRilGHo4XTOfX3pPDKOjdysgQ49PWeANPbJWj5gRj9c5iM95k=",5000951507456107664,-8960405386323365807,-8808594496720127091,-1377935461960786894>(),
                     var8,
                     var5
                  );
               }
            }

            a(var2, var3);
         } catch (Throwable var7) {
            b = true;
            e.warn(
               (String)com.yiyiaddon.m.b.a<"s228ddsbpsy1k2","z30fBv8lPSa9etoCg73OCqz2AHybfn8f0lp3lpqW8ISjNY12blYYST6hcQ7EkY9JXrNCAXu2oI+lGNCcP9JtuwmpKTRzo+tkTKCjgLXQDkUftWaYrHoUJ8t0De1uPg==",-3597821753860741112,-6652303270779047398,4598905537728348610,-1020335545679970061>(),
               var7
            );
         }
      }
   }

   private static void a(boolean var0, boolean var1) {
      if (c) {
         switch ((int)com.yiyiaddon.m.b.a<"s37blacglweh3w","wTEO96zNonpsrGnB4558vrtHaD7Tee1HGfbxARHTRRo=",-999879819704841041,3790465068255443969,-2133589855250946238,-3486774026718992329>()) {
            case 2143184123:
               return;
            default:
               throw null;
         }
      } else {
         c = true;
         StringBuilder var2 = new StringBuilder();
         if (var0) {
            label38:
            switch ((int)com.yiyiaddon.m.b.a<"s1tenk1n6rltgj","BCV3vMBJ4qZhtPG8unJQTqcxiq5GVP0Zoz5FLoiMpHU=",-3815559567664637683,2826154882860273564,2111841328583844747,-5109444347463904169>()) {
               case -132762971:
                  var2.append(
                        (String)com.yiyiaddon.m.b.a<"sd5gjdw4wy23u","kNVFYXboa9SsLdINOR1wkIt9qxq4aJIqQWROgSyrRNCcD573lbFPl825YVg=",-8842857389801956480,-6520300269087440890,-8229081202821274168,-8366133999808049674>()
                     )
                     .append('(')
                     .append(
                        (String)com.yiyiaddon.m.b.a<"s335ysdmf5n7nw","acdt9EObMAlvondIITA+pE9ywTYr2HhKbtYTc3quehU3P+rv",-2879458660808005217,4440389423574394759,-8189632337363573666,-6677712908559752101>()
                     )
                     .append(')');
                  switch ((int)com.yiyiaddon.m.b.a<"s3icd22rg0h8bx","ClrWfvqlIR8F22Cz3OiO6tbO1ENRXINii3rEZtSixFo=",-1191031783929243728,8506535244990061030,1909072494961460913,-9120030216355018545>()) {
                     case -995379626:
                        break label38;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         if (var1) {
            label33:
            switch ((int)com.yiyiaddon.m.b.a<"s3el0ezw5viny6","Fjnr6vKumDrNCUJwQ8uwDwJwyyMPdQado/0byWMt//8=",8165516600991832330,3567260517366771038,-4963319330050922151,4834217173377941286>()) {
               case -524909286:
                  if (var2.length() > 0) {
                     label30:
                     switch ((int)com.yiyiaddon.m.b.a<"saxxjv7n9elno","BhgPXOFVJm3ez7bAyQKbO76HmpzemAvCLEoAjuxkrq0=",2013582750196665970,-819548538506572868,-3661865460905692919,5535635060453766097>()) {
                        case -484834658:
                           var2.append('、');
                           switch ((int)com.yiyiaddon.m.b.a<"s1lfvk3lb2ekol","BF4XiVwU2yKQ8D/mL4bWR3wJunI5ubfPkXBWihgbD/4=",-1907979765010353263,3793387821692211438,5263852152790173938,5722906109085030791>()) {
                              case -690135361:
                                 break label30;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  var2.append(
                        (String)com.yiyiaddon.m.b.a<"sgj3c33lcr5e7","w2hCnzZnrzhRilGHo4XTOfX3pPDKOjdysgQ49PWeANPbJWj5gRj9c5iM95k=",5000951507456107664,-8960405386323365807,-8808594496720127091,-1377935461960786894>()
                     )
                     .append('(')
                     .append(
                        (String)com.yiyiaddon.m.b.a<"s32owcbqz3eft5","scZKNPCsLrD6lCYbfmpOp0ksJa5LRirmiAjJoqpNJu1DMz2+",-8487957781477564871,8196724038413272954,4063635312244195117,8778823249231880456>()
                     )
                     .append(')');
                  switch ((int)com.yiyiaddon.m.b.a<"s3uephl26e12ax","6HdTJoKFE/+a3r3R89Ifo/aFzBh1eahujCt18EXXk6A=",-516590623727758364,-6073613382964002351,-4339515451753521927,7418480278785562824>()) {
                     case -1603397757:
                        break label33;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         e.info(
            (String)com.yiyiaddon.m.b.a<"s3l9h5qsgixr4j","uz/SOQ+MV5dEJWZssckbr5T0XGOgW0K8l9mOXJRJVqQSPwJw/4PThDu879RIsBsEgh1feVNSA+Kj15gTGuCf4W91JJAZ6pt/3gv8DbvtDp46UIYDMclQ+6fWy26EIXOMXfUh2LUnrdI5RVcueNKMWngzTBNgdYBwnCuCaj0C1hQ=",3239105564178155940,4128147768618564109,5947634843167487754,-128867877238617814>(),
            var2
         );
      }
   }
}
