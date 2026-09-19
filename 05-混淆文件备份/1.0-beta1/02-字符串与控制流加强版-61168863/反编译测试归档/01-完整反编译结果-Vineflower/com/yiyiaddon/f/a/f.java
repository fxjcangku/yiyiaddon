package com.yiyiaddon.f.a;

import baritone.api.BaritoneAPI;
import baritone.api.Settings;
import baritone.api.Settings.Setting;
import baritone.api.utils.SettingsUtil;
import com.yiyiaddon.l.j.i;
import com.yiyiaddon.l.j.l;
import com.yiyiaddon.l.j.m;
import com.yiyiaddon.l.j.n;
import com.yiyiaddon.l.j.o;
import com.yiyiaddon.l.j.p;
import java.awt.Color;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.DoubleFunction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Vec3i;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public final class f extends com.yiyiaddon.l.f.a {
   private static final double bs = -1.0E7;
   private static final double bt = 1.0E7;
   private static final float eE = 220.0F;
   private static final int rZ = 512;
   private static final float eF = 320.0F;
   private static final int sa = 120;
   private static final String BU = (String)com.yiyiaddon.m.b.a<"s3avo8psddkkvt","yj+DwbRFFzlIvqs4tGIO4yTy7vyxC6wWD4T2HGlRqHi0PjEUhhtoCK3W",6762551348652480829,4602983539911819401,4443369102696621447,8138915550572735687>();
   private static final String BV = (String)com.yiyiaddon.m.b.a<"s1sf799d0aanv0","YlgwLHD74viywBHxtDBmV5zg8FConyHwq6sqxJ1DfZXILJ/PFQksGyd3m2MGKcofoEaH3A==",5780618185331927526,3843354510242717039,-5203259095327273609,1641354174171876670>();
   private static final float eG = 96.0F;
   private static final String BW = (String)com.yiyiaddon.m.b.a<"s2gi392m6g4l0c","/0Y7DoAW6VhqlGbekDIduOTcy0rHKkpJLnsLBO2C",1797218603269026835,-4426470410998635369,4664318495964403586,-1129832959482936548>();
   private static final String BX = (String)com.yiyiaddon.m.b.a<"s15k5m77mjk3kx","gzBw1GObhN0+S6R1rqiCVzK5ybsyQvGAuJDD4Cu+",-6517438207830350756,-3124598514103252520,6357349081992326,-4911305633942987806>();
   private static final String BY = (String)com.yiyiaddon.m.b.a<"s24qdj7u3tf20m","R7ja/+6hHvsYN2WObh/hVrJBktiwqZb5oexKhcQl",-2250886995078566310,-6778568991285060541,-2659833389545886532,3284830312295696401>();
   private static final String BZ = (String)com.yiyiaddon.m.b.a<"s1xrpwv0n21r02","LzPfUFkKB3ADOXiipfjoWzHBKjad7TtKe76T6EkN",-3223656893569829137,3354982175601431428,8080091114676798431,-4276419602816102524>();
   private static final String Ca = (String)com.yiyiaddon.m.b.a<"s2y9vj454zeuf1","6lJovc4lNYVcDm3fVxpZsqHlgqI84f5AjKasDpmo",-3069210919849722775,5059707239228768082,-8731736458470001459,-4747192076515512786>();
   private static final String Cb = (String)com.yiyiaddon.m.b.a<"s373dtlhtqczxy","0bGy+3cxTLmiwOjIUxm3HV86JdXFNFuOu1KYK6Si",-8298084175914147136,7916151526356160751,1964150151583847965,7118654194391791801>();
   private static final String Cc = (String)com.yiyiaddon.m.b.a<"s4jxujpf0kl1e","9LJhoMPGJlwJZLLTgxBFqGSQXvQrTZ8fCLNQcMeV",-7948476099010145457,-2368429658498655874,5223584770802524510,-3084654094170820761>();
   private static final String Cd = (String)com.yiyiaddon.m.b.a<"sa39i4lv5o8z","hOo1vjrLzgArjbNnihw8eyrhD61CKT5dibk0rXZD",8811555493652065438,-566657559637088613,2278247070810556566,8630481679677905825>();
   private static final String Ce = (String)com.yiyiaddon.m.b.a<"s1ntsjbsvyfwu","2l6jisyXrajVpmI3JWKY+4j7sZag1kkRrZzitNqw",1182915719024907414,6886428775651045954,7601639178592787368,-4418786371926631501>();
   private static final String Cf = (String)com.yiyiaddon.m.b.a<"s1uts0yw3lsm3t","VwssjYguTCDO1fudKrrPdMy4c+GQbUy7mb/K2dKb",1302608406586012835,-2685039851385511560,-4532389375920995333,2129901808652326989>();
   private String Cg = (String)com.yiyiaddon.m.b.a<"s2p831arpumfe5","hY7HBqU1C2288F9D0gsSOJddWhmyawXU9qiBvw==",-166855876597815614,5528550339263305687,6962227435003929334,8865210565468191848>();
   private final List<f.b> cz = new ArrayList<>();

   public f() {
      f.a var1 = new f.a(
         (String)com.yiyiaddon.m.b.a<"szfq4pexctnfi","v1O/hm+Vmtt7SCasptXSNIgSd4IWQROozItus4RwjjE+lP0902A=",-3297760574070072061,8571505348577763485,-297997542015994800,-6813960450924156335>(),
         (String)com.yiyiaddon.m.b.a<"s26v0gcm0d9mxr","HaRknixqKqJZ6fKcH2VVEdvm37kFjw1dY/vSYU7DUR9mSzZTdQBCX4ZGSIIwzUOZfkdpWl4VnjpddA==",-8043801526061580063,-6713506638185222977,-3658090470014268707,-4169343462130964344>(),
         (String)com.yiyiaddon.m.b.a<"s2gi392m6g4l0c","/0Y7DoAW6VhqlGbekDIduOTcy0rHKkpJLnsLBO2C",1797218603269026835,-4426470410998635369,4664318495964403586,-1129832959482936548>()
      );
      f.a var2 = new f.a(
         (String)com.yiyiaddon.m.b.a<"sl18mp5jr2k0u","QDA7xPsFVY1DmB8tnM0yH/aE2jhM2gaOOYy3DQXYrW8=",-185143840841906344,1837664698108809317,3324501816256531008,962342953991082887>(),
         (String)com.yiyiaddon.m.b.a<"s1t4qjl2hwpobc","BSC+tSLbsCK4GxRyZZjf/Efl1768qpchrNar2BgnOVVFXAtmYT2UNMpDsmT2pGKP9dBjdg==",-9158253834572600221,7831820647190257937,4874452261060576716,-2825515703714163203>(),
         (String)com.yiyiaddon.m.b.a<"s15k5m77mjk3kx","gzBw1GObhN0+S6R1rqiCVzK5ybsyQvGAuJDD4Cu+",-6517438207830350756,-3124598514103252520,6357349081992326,-4911305633942987806>()
      );
      f.a var3 = new f.a(
         (String)com.yiyiaddon.m.b.a<"sr4murl52s2sq","Nozp1ft8pLPqHbylRh9tnEax+2KwSS+hoSM4rnaZ6xY=",-7005567476396107792,5122351196333354541,-3758609801285555567,3082568194425976966>(),
         (String)com.yiyiaddon.m.b.a<"s10rvlr1mvg55e","nvQBxr/FgD2SJ1SPxKhZtRE5jxC8sBNOBN0OCyltd2xk7ht4nBmh2WYv2xled6+JUtZMPuUFF3uubooNCfcJaQV3OihotWZ1WiDgQO2mf7R+EpJB/lfHXM6m",5553035849875404639,-5668082700570198763,-4404056472174506583,8196844518963681613>(),
         (String)com.yiyiaddon.m.b.a<"s24qdj7u3tf20m","R7ja/+6hHvsYN2WObh/hVrJBktiwqZb5oexKhcQl",-2250886995078566310,-6778568991285060541,-2659833389545886532,3284830312295696401>()
      );
      f.a var4 = new f.a(
         (String)com.yiyiaddon.m.b.a<"sibmo5eml3o8a","OBcsSURjTFeTeR7zghenCCh+m2eRib0yUEul6MGqRr8=",-1180685247268873814,4138895762166596258,-6797199560741974796,-3723049255868747867>(),
         (String)com.yiyiaddon.m.b.a<"s2bt2ctp738lb7","2arqAdeS0fhjfY1uG5cWTbgJI/1N4HccnDGaC2JcBPbZ6ipXaRE=",-1708730572549783634,-5445147059653982834,2633487506260089488,-1728140081464179699>(),
         (String)com.yiyiaddon.m.b.a<"s1xrpwv0n21r02","LzPfUFkKB3ADOXiipfjoWzHBKjad7TtKe76T6EkN",-3223656893569829137,3354982175601431428,8080091114676798431,-4276419602816102524>()
      );
      f.a var5 = new f.a(
         (String)com.yiyiaddon.m.b.a<"s26yj81tf6lnsb","//iQDQOJR+BtqxtsPyg3ECpr7cjk9DsQgGQlPos9+PM=",1783894005415870030,6975218381511233460,-1066736581961175681,-3374142200391239416>(),
         (String)com.yiyiaddon.m.b.a<"s3pndb5c38xyk2","0R0YR8jO4xFsI0ZYwFA6mEG542QeaiaNAjOoLKuPbIEngkeCUxWJjHu08fWStlgUQr1yp7SmMr3fjQ==",-158788261547923340,-3866183297804636810,-7737283948897027593,8223678222152255518>(),
         (String)com.yiyiaddon.m.b.a<"s2y9vj454zeuf1","6lJovc4lNYVcDm3fVxpZsqHlgqI84f5AjKasDpmo",-3069210919849722775,5059707239228768082,-8731736458470001459,-4747192076515512786>()
      );
      f.a var6 = new f.a(
         (String)com.yiyiaddon.m.b.a<"s317n5hqytr7nh","7G5Y7Gtw0F627vIlyPkmsM5GYCZjT2DAWFYUvNM5Huo6Nmr5",-2864114204490294300,-9010806858729604940,438718029248416121,-8198246613485532153>(),
         (String)com.yiyiaddon.m.b.a<"s1dtuie36t8rdx","Z8Voowie0NM4+sE+TPXBU31u+4BCDyGZM+8Uuw47OdzXF63w4PT++cjIy9s/dzzuatjGLmBgtSu3oSUAevJFw8p9g7x0IJrMUhHt5+XZ1G4VBZkbY7Q9kA==",-2394382269287595527,2571019623377190806,7931866120592759077,644622709187537152>(),
         (String)com.yiyiaddon.m.b.a<"s373dtlhtqczxy","0bGy+3cxTLmiwOjIUxm3HV86JdXFNFuOu1KYK6Si",-8298084175914147136,7916151526356160751,1964150151583847965,7118654194391791801>()
      );
      f.a var7 = new f.a(
         (String)com.yiyiaddon.m.b.a<"s11npryisor92q","GODzhjfiR1eWSAxCk6DDwfSr9L7+LmpCpZmOl2heZOzIX2yR",3365273676250528401,-3352820036708239719,-3397955516071494241,781096254237867283>(),
         (String)com.yiyiaddon.m.b.a<"s406sizw28obg","2n4joYlIeu+2neY4CTnoReDnBCLbn2eBfoB/PuGt7DWQ+h8mcNM17lnWmcGRd0FavBbdfnDhtvk0drf3/f6nkPA1wdrx+R1F174kcWhoSgfcqg==",-589361069122430210,-412069486759568305,-3484309207638011540,5784065565033250386>(),
         (String)com.yiyiaddon.m.b.a<"s4jxujpf0kl1e","9LJhoMPGJlwJZLLTgxBFqGSQXvQrTZ8fCLNQcMeV",-7948476099010145457,-2368429658498655874,5223584770802524510,-3084654094170820761>()
      );
      f.a var8 = new f.a(
         (String)com.yiyiaddon.m.b.a<"s1l6l2ylo5c3ot","g4mtEvAMDAtkL1439dNS7CMCeWjYX0+ul/dLXoOINfGfwnBO",-2408120915118184363,-3416091201058477457,8626343500591377363,1400791560389001168>(),
         (String)com.yiyiaddon.m.b.a<"s1ui44tedzqx8a","CyOBDfbyws1HXuB3LuwCEdZI2+d1ltnMC8rBq6nReRhziUv2HFEl224aIqg6QakLhig4jTcjPploNvzWlfUEQXO0wPMZMTm36m/Ki2agV9UBRg==",-3761181270612244325,7491647117435767986,-4412822334673384771,638755267918645020>(),
         (String)com.yiyiaddon.m.b.a<"sa39i4lv5o8z","hOo1vjrLzgArjbNnihw8eyrhD61CKT5dibk0rXZD",8811555493652065438,-566657559637088613,2278247070810556566,8630481679677905825>()
      );
      f.a var9 = new f.a(
         (String)com.yiyiaddon.m.b.a<"s2lq71ah07vemj","v/p90LP8fb+3NpGemNpr7JlNcNfCItkduZ8gvI99EzZkThQEvP8=",8547457262580643930,6540034851759506217,728646495481065151,790575693727606993>(),
         (String)com.yiyiaddon.m.b.a<"s30764d5fdy4gd","p6fFBgPuI4TQoMxKG4cYwzT0KHDGUQuCHejO4usjNm7SsDXxnIKRwwNgLHgxoOFz5RVN1nwU44pVH730vvOUGQ==",2690777170322083745,849044901177569375,-1932160155038171208,636392723723622139>(),
         (String)com.yiyiaddon.m.b.a<"s1ntsjbsvyfwu","2l6jisyXrajVpmI3JWKY+4j7sZag1kkRrZzitNqw",1182915719024907414,6886428775651045954,7601639178592787368,-4418786371926631501>()
      );
      f.a var10 = new f.a(
         (String)com.yiyiaddon.m.b.a<"s21sc2ji2lewza","8l8waWf26YAQPyct35DaMV/nz27mXjh1FAl56WdBmE897OdtVGN9iy0IOg9envco8WM=",4394206845577917372,-5502550465629814890,4944980665536560232,1758113098954242588>(),
         (String)com.yiyiaddon.m.b.a<"s2lvvr1suj980z","h5z/mYhL0aSPzGEggzZVB5AaJ8ecTYHSHiOPy9yi2aCN1pooFeKZAp8dvP3MlDPrRYUgV3jThcg4+ARVzBzwviAKiEKtOg==",1202062941178492605,-8207946066510148750,7067123230076048501,-5469187715000363669>(),
         (String)com.yiyiaddon.m.b.a<"s1uts0yw3lsm3t","VwssjYguTCDO1fudKrrPdMy4c+GQbUy7mb/K2dKb",1302608406586012835,-2685039851385511560,-4532389375920995333,2129901808652326989>()
      );
      Settings var11 = BaritoneAPI.getSettings();

      for (Setting var13 : var11.allSettings) {
         if (!var13.isJavaOnly()) {
            String var14 = var13.getName();
            e.a var15 = e.b(var14);
            String var16 = var15 == null ? var14 : var15.a();
            String var17 = var15 == null ? var14 : var15.c();
            Object var18 = var13.value;
            if (var18 instanceof Boolean) {
               var1.a(var16, var17, new n(() -> Boolean.TRUE.equals(var13.value), var1x -> a(var13, (Object)var1x)));
            } else if (var18 instanceof Double) {
               var2.a(
                  var16,
                  var17,
                  a(
                     var13,
                     0.01,
                     (String)com.yiyiaddon.m.b.a<"sms9zpuccpghr","4USHtXaDerNbg3LNA6M56k48eEIdsrf8CF3GaeWfB/Snt6NA",-4333569017517947442,-298868521726710426,7796416411228974240,-7135850824305701875>(),
                     var0 -> var0
                  )
               );
            } else if (var18 instanceof Float) {
               var2.a(
                  var16,
                  var17,
                  a(
                     var13,
                     0.01,
                     (String)com.yiyiaddon.m.b.a<"sms9zpuccpghr","4USHtXaDerNbg3LNA6M56k48eEIdsrf8CF3GaeWfB/Snt6NA",-4333569017517947442,-298868521726710426,7796416411228974240,-7135850824305701875>(),
                     var0 -> (float)var0
                  )
               );
            } else if (var18 instanceof Integer) {
               var3.a(
                  var16,
                  var17,
                  a(
                     var13,
                     1.0,
                     (String)com.yiyiaddon.m.b.a<"s3scs2eb6b6bgq","4D5MZwYv09iJRJbuWEf4Kak165oHTyYmQdR+breBUJk560Ap",-2949353840574770227,1512727362048383415,-336600415744650000,3974064504711712602>(),
                     var0 -> (int)Math.round(var0)
                  )
               );
            } else if (var18 instanceof Long) {
               var3.a(
                  var16,
                  var17,
                  a(
                     var13,
                     1.0,
                     (String)com.yiyiaddon.m.b.a<"s3scs2eb6b6bgq","4D5MZwYv09iJRJbuWEf4Kak165oHTyYmQdR+breBUJk560Ap",-2949353840574770227,1512727362048383415,-336600415744650000,3974064504711712602>(),
                     var0 -> Math.round(var0)
                  )
               );
            } else if (var18 instanceof String) {
               var4.a(var16, var17, new m(() -> String.valueOf(var13.value), var1x -> a(var13, (Object)var1x), 512).a(220.0F));
            } else if (var18 instanceof Color var19) {
               com.yiyiaddon.l.g.a.d var21 = new com.yiyiaddon.l.g.a.d(var19.getRGB() & 16777215, var19.getAlpha());
               this.cz.add(new f.b(var21, var13));
               var5.a(var16 + "", var17, new com.yiyiaddon.l.j.c(var16, var21));
            } else if (var18 instanceof List && a(var13) == Block.class) {
               var6.a(var16, var17, this.a(var16, com.yiyiaddon.f.a.c.c.BLOCK, var13));
            } else if (var18 instanceof List && a(var13) == Item.class) {
               var7.a(var16, var17, this.a(var16, com.yiyiaddon.f.a.c.c.ITEM, var13));
            } else if (var18 instanceof List && a(var13) == String.class) {
               var9.a(var16, var17, this.a(var16, var13));
            } else if (var18 instanceof Map && a(var13)) {
               var8.a(var16, var17, this.b(var16, var13));
            } else if (var18 instanceof Enum var20) {
               var9.a(var16 + "", var17, a(var13, var20));
            } else if (var18 instanceof Vec3i) {
               var9.a(var16, var17, a(var13));
            } else {
               var10.a(var16, var17, new l(() -> a(var13.value), 320.0F));
            }
         }
      }

      for (f.a var23 : List.of(var1, var2, var3, var4, var5, var6, var7, var8, var9)) {
         var23.fb();
      }

      var10.jh();
   }

   @Override
   public String E() {
      return com.yiyiaddon.l.a.w(
         (String)com.yiyiaddon.m.b.a<"s1bmmarg00g62x","sVNzGVzub+On3PZEcLfCxpg1qoR4dtmifcPPH4L5oy0fDKZlIm7iwc+FSSURQZrr",1867495419628772914,-3631565782465011993,4280939302646882146,-4922502448628713279>(),
         (String)com.yiyiaddon.m.b.a<"s1dhlraj00wi59","L//NFQ62Q5PliZY/V+InJNgITsxGA/FAoHcea1gMK2m8OGvJFPxYSaSaCySamH/kjYBhegNbTGwRb3veTjg=",-1633380343952479987,-430416989168983051,-8639453795136537662,2784486446141704844>()
      );
   }

   @Override
   public String F() {
      return com.yiyiaddon.l.a.w(
         (String)com.yiyiaddon.m.b.a<"s2jp9jb1fkkot4","Nqv2abZMZC/iPWt0s3XMk+qZh+lBTi8IiXKDLOf894sISpE5ol8+VzKgqBptEqRkW2fabxv4d9TeN+MwBlf9I3tTgte5tCzLPu9PDISJ24JFde+2m4mScg2cFFSeMft32tHL44eLi1q+7j4CYFY9bQ==",1126918612938989314,-6873124272448540840,-8556268541234278804,-3501220320622015764>(),
         (String)com.yiyiaddon.m.b.a<"s1re9sgeuzzqbc","WDzPOh9/eViWLxtZukhC1UAmJn6X99mlj2oxY7upjPYpY53nnADV2cPH6DLahL/NBtrUvPaNia8KXpJ8dzep0f7McVls8CU6F6eyvM5sHLnUU0YoDFLEhBdYM8Be0aj9BrU+8CgX1gGmQurR9rayz43p4eCQXa65GGzkV2Dz3vrSMSxN5NxpSGaluE7a1rHVY+0WWFBanb9luqfTUXMTTOiJxpeyu9jViABjUe1g2QN4YpjRzCgpIiQ3Z5g=",-5713027806504952176,1521129685778437497,-3365234265677898041,5550188401671135603>()
      );
   }

   @Override
   protected float n() {
      return 6.0F;
   }

   @Override
   public void bf(String var1) {
      String var10001;
      if (var1 == null) {
         label45:
         switch ((int)com.yiyiaddon.m.b.a<"s38qnhqv1i2t49","Fg91dQFkx91HiLDZZZC52v9u6EW2d5cJlTxuigtFcYo=",1751450251555240968,5015112111370460662,614712370810928175,682405913483152153>()) {
            case 213049123:
               var10001 = (String)com.yiyiaddon.m.b.a<"s2p831arpumfe5","hY7HBqU1C2288F9D0gsSOJddWhmyawXU9qiBvw==",-166855876597815614,5528550339263305687,6962227435003929334,8865210565468191848>();
               switch ((int)com.yiyiaddon.m.b.a<"s3cmngy0yjibji","s4sjJ7cvO6pTm/wwbXNTIjGIykLiwi5SofOGLGOL2JA=",786732795304477208,2626304022896191207,-2409535413532956036,3959735273272163971>()) {
                  case 1846856858:
                     break label45;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = var1.strip().toLowerCase(Locale.ROOT);
         switch ((int)com.yiyiaddon.m.b.a<"s1dfpduuls4y4s","+XZ2nUs4teCpvF7zC7QHHxNfy+bS/H6qal/jfO5tDdk=",7821729499084384557,-3892485225555758180,-4758081808577638674,-838503691492541964>()) {
            case -593580181:
               break;
            default:
               throw null;
         }
      }

      this.Cg = var10001;
      super.bf(var1);
      boolean var10000;
      if (!this.Cg.isEmpty()) {
         label38:
         switch ((int)com.yiyiaddon.m.b.a<"s9mkhrz9uihm","Djc5uyOCUVFuqel6LXBitfdZR3rX0bi1L16W/pJ1V4I=",2319521087327026721,-1391343362326127175,8494096497492519473,7766404485930133672>()) {
            case 667343558:
               var10000 = true;
               switch ((int)com.yiyiaddon.m.b.a<"s3lsxovm5hifnj","d4csdIwyRnp5vnNoTl/11owdOFrATix/vuPF0hUe+Ng=",-5830100464396243316,2865068972165397872,-6127419479556730196,9126125388527123886>()) {
                  case 405891985:
                     break label38;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = false;
         switch ((int)com.yiyiaddon.m.b.a<"s28x66klo02pf5","n4yQcQR0POtnD5xU5cJ8pxzJK40L4dMAFB0+Hn2bsoY=",-2473110493066427247,-6124278261564000661,-728048030762002482,1320762277830928269>()) {
            case -723041628:
               break;
            default:
               throw null;
         }
      }

      boolean var2 = var10000;
      Iterator var3 = this.do.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s3frxy09zndo9n","iQukcoBz0T+kiIlb//2Kz5MC13Jx2FKn5AEmWnqVrhg=",-1540654206601414691,8128086862815823935,8644478098945522364,133528070303527646>()) {
         case -478910163:
            while (var3.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s3lisroga34psx","H63sLSw2VJ/lrWS47AEd7MwX2oKYCsMc58Nw/iVq5bI=",-6647955934519034735,-2557657278706244065,-8133211594598891235,-2670598136928777236>()) {
                  case -918011311:
                     com.yiyiaddon.l.j.h var4 = (com.yiyiaddon.l.j.h)var3.next();
                     var4.a(var2);
                     switch ((int)com.yiyiaddon.m.b.a<"s11kq795jwe8cj","v4LgYSRcTHA/frWrvNyvktn5N5CDPqOF9GTdY9NCm/U=",8211992572861760915,-8004093302827699807,5434783278809407463,2465400150322725779>()) {
                        case -1019017278:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return;
         default:
            throw null;
      }
   }

   @Override
   public void a(float var1) {
      super.a(var1);
      Iterator var2 = this.cz.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s12qhfbtv5cq93","aF8rmhx9vYKNYFB/aJYgAaXeSsCeEz2tdZgqn+oS+wo=",-691333102270148421,2520545973985010129,4335458875703020223,-5838319208658806912>()) {
         case 1544787597:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s2g3vx1x941yzy","qq1LPwsmXoLEW7QBp4Lv8F0x/ZHDqvBmYv921/yeec0=",-3738111690206879937,-5698514416378620078,-5460660281460791110,3245263358371033190>()) {
                  case 765379941:
                     f.b var3 = (f.b)var2.next();
                     var3.jg();
                     switch ((int)com.yiyiaddon.m.b.a<"sb36yh6pxookn","zlvNHn4HWBG2w6UZC0Kcb7VM5oss/CxHywTTChqWRyk=",9042606877862056728,-1331237910929939134,-3587105705364640681,8629280921758040422>()) {
                        case -1184682187:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return;
         default:
            throw null;
      }
   }

   private static i a(Setting<?> var0, double var1, String var3, DoubleFunction<Object> var4) {
      return new i(
         -1.0E7,
         1.0E7,
         var1,
         var3,
         () -> {
            Object var2 = var0.value;
            if (var2 instanceof Number) {
               switch ((int)com.yiyiaddon.m.b.a<"s2i5i0ffpneojk","SYFBqPStv84/DjClXhRjWoE56blc+fVMm0g/Q3NCrGg=",144950660328173765,-8006547444760462405,2698974783914544727,5857417318864215161>()) {
                  case -1176641660:
                     Number var1x = (Number)var2;
                     Double var10000 = var1x.doubleValue();
                     switch ((int)com.yiyiaddon.m.b.a<"sql4xkv3uo61c","ziqr4fjrl2OuJ1lHlKMMKCLpnzyegvpLRWrs4m8eO2E=",-6749796624667783975,-8694616166661949415,46259230944182085,-3899622983831804358>()) {
                        case 467745079:
                           return var10000;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               Double var3x = 0.0;
               switch ((int)com.yiyiaddon.m.b.a<"s6xy54ig1a19x","FX/FLUbsJ4EHORscZxXHyavH9vLA3ln8LYbSVlAOhLs=",754346142803178586,3761561122777508498,-6732109791316778444,-4371986110093498941>()) {
                  case -448513175:
                     return var3x;
                  default:
                     throw null;
               }
            }
         },
         var2 -> a(var0, var4.apply(var2))
      );
   }

   private static void a(Setting<?> var0, Object var1) {
      var0.value = (T)var1;
      SettingsUtil.save(BaritoneAPI.getSettings());
   }

   private static Type a(Setting<?> var0) {
      Type var2 = var0.getType();
      if (var2 instanceof ParameterizedType) {
         switch ((int)com.yiyiaddon.m.b.a<"s8frj5icn4p4n","j5WlF1gNJlNtaaeZpwJLlxFmy50V+Y7dW66dU03JZDU=",79960280577658301,-1480474391339433098,5045338378500314365,1190696627781800312>()) {
            case -1288934853:
               ParameterizedType var1 = (ParameterizedType)var2;
               switch ((int)com.yiyiaddon.m.b.a<"s1hxzvha76vejs","w9RptfdgrurZGHB8xnxzwYaqMB0f3Zbdguc5khGtnVo=",5932220786713188121,7655429238893789051,4466601881750832669,7156976728837570389>()) {
                  case 738911527:
                     if (var1.getRawType() != List.class) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1l15q99198ypn","TgbakDFSD3O2oayx0RxYUATGQJf11LoWwNBbGPMc008=",-1699844623110744969,-2989507151032652871,-8140914744925689780,6875402879998765520>()) {
                           case -1280258516:
                              return null;
                           default:
                              throw null;
                        }
                     } else {
                        Type[] var3 = var1.getActualTypeArguments();
                        if (var3.length == 1) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3rpaz78og4pfc","usiEnE9diLYRQlixiWlwNS+0iW9XVrvwDmB/wRo267I=",2283740907786610779,8663880389924957930,-1346627012905372742,-4376169577893606176>()) {
                              case -507691384:
                                 Type var10000 = var3[0];
                                 switch ((int)com.yiyiaddon.m.b.a<"s150zgpqjgul4f","3/9rFcDou9d6hUVFwPsb4EoQJnJR0RDM/tzABGenFJY=",-508934668669909050,-5567205120686876658,-4300091206420005748,3653287642415787833>()) {
                                    case -1913281903:
                                       return var10000;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           switch ((int)com.yiyiaddon.m.b.a<"s1f2qknhsv5jjg","2aHGx/QFCz9raGOT5W+lHV41qViqF1SmaNsLM1akpzU=",2945754110534176098,-6322208892733912083,-3036874418521742825,-1774801659505273778>()) {
                              case 645080522:
                                 return null;
                              default:
                                 throw null;
                           }
                        }
                     }
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return null;
      }
   }

   private static boolean a(Setting<?> var0) {
      Type var2 = var0.getType();
      if (var2 instanceof ParameterizedType) {
         switch ((int)com.yiyiaddon.m.b.a<"s3tmzlp23o9og6","einfGzEK78kJMNuI7XFK+x/rBg+HNsznHliLgLdTfEA=",4414597459287361637,7660011278324226259,-3133120433989714548,-4164500249123686938>()) {
            case 1030816149:
               ParameterizedType var1 = (ParameterizedType)var2;
               switch ((int)com.yiyiaddon.m.b.a<"s1ebbah3e6de69","HWZVeESMgoEhPjCEb4CEPodgDAhhXT5tQ+pDBriMhnc=",935373630669215504,-6812638355746837593,2456486984736258453,5463027408241635234>()) {
                  case -1032011385:
                     if (var1.getRawType() != Map.class) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1s7gpi4zb5cw0","Ml6XgyQrtmy3ggQHsHjl4JPh4StqruE6T2duBQTJa5c=",-8316309148335184638,1702075375101992076,8431260116459274060,1937979550318785363>()) {
                           case -91720278:
                              return false;
                           default:
                              throw null;
                        }
                     } else {
                        Type[] var5 = var1.getActualTypeArguments();
                        if (var5.length == 2) {
                           switch ((int)com.yiyiaddon.m.b.a<"s189ge0sgmwfgn","w/8VkSp61q7+pphw0y0yQwFuqWITWFAElw1CVuZC01k=",-2027716187095255227,-4268486659663502783,-240831168260439996,6809054854788788145>()) {
                              case 1068930592:
                                 if (var5[0] == Block.class) {
                                    Type var4 = var5[1];
                                    if (var4 instanceof ParameterizedType) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s1x9hgycv2ynuk","mFEjGY7ZLxF64wJ1YeF2SQhh3stXV4qx8sjYvr1Lyec=",2586681617978164341,-4633676036182216231,-673779057541152092,6347673947655798074>()) {
                                          case -1477011794:
                                             ParameterizedType var3 = (ParameterizedType)var4;
                                             if (var3.getRawType() == List.class) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s291z0te5gnvcd","+Z5pg5r3dwqy+A11KUZaQXvopQ0kF7D8CM6Rdsg0+5c=",8197292685421524922,5728492097691322864,6773634768009398729,-2270214540636312702>()) {
                                                   case 1721017460:
                                                      if (var3.getActualTypeArguments().length == 1) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"s33imildslvpf2","DAoST5cIIC/+LUL+gjxZEAW3IxjoENr7H44nGEyX80o=",8903693582332690884,3305785358593948878,-5474156051800694443,3820818942786173845>()) {
                                                            case -2090855202:
                                                               if (var3.getActualTypeArguments()[0] == Block.class) {
                                                                  switch ((int)com.yiyiaddon.m.b.a<"s15tx5zzl3pmdu","oyLdN4QxdzE2nt+qY5H1Ir+v/kmkldN0OfDF2Op0/PE=",5078616550624093194,-4168201807812297908,-3841645497257553441,-3766645420760083261>()) {
                                                                     case 1007107011:
                                                                        switch ((int)com.yiyiaddon.m.b.a<"s3udozrt6blns5","TQHkjFq11Mko/Ac9LuBm4z3N8bajSs2GvVZU1xFkep4=",-6427713315784569551,6972520595522547599,-2699138506583989661,-9199153305099794457>()) {
                                                                           case 1900260831:
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

                                    switch ((int)com.yiyiaddon.m.b.a<"ss1j0t5vua75g","2F6zq42lwhnkdF+I2+97JlrMvCfgy9W+D0eE/yw5fG4=",1967995056290884105,-2083129194550171067,6293414655830957791,5731937547316550816>()) {
                                       case 2052906681:
                                          return false;
                                       default:
                                          throw null;
                                    }
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s2l800s7w1ut2n","lrEyiBOcI0y3LLB0TWXGBMenWPDNLtmvIMxPxInZuW8=",-1918034441723569306,-6931397568736778744,-4911435483824575558,3553175912400918159>()) {
                                    case 1636341127:
                                       return false;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        return false;
                     }
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return false;
      }
   }

   private com.yiyiaddon.l.j.g a(String var1, com.yiyiaddon.f.a.c.c var2, Setting<?> var3) {
      return new com.yiyiaddon.l.j.g(
            () -> com.yiyiaddon.f.a.c.a(var3, var2).size() + "",
            () -> f(
               new com.yiyiaddon.l.h.g(
                  var1,
                  Minecraft.getInstance().screen,
                  com.yiyiaddon.f.a.c.a(var2),
                  () -> com.yiyiaddon.f.a.c.a(var3, var2),
                  var2xx -> a(var3, var2, var2xx, true),
                  var2xx -> a(var3, var2, var2xx, false)
               )
            )
         )
         .a(96.0F);
   }

   private static void a(Setting<?> var0, com.yiyiaddon.f.a.c.c var1, String var2, boolean var3) {
      List var4 = com.yiyiaddon.f.a.c.a(var0, var1, var2, var3);
      if (var4 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s21ea9sfnfoc7h","8MEIxewUyyKfRmG+o6SSPrTjWRxWxuBfnU36KXtmQi8=",-7810430060782636208,-5016653654502413684,5434913804438528183,-405317128083410029>()) {
            case -1420020797:
               a(var0, (Object)var4);
               switch ((int)com.yiyiaddon.m.b.a<"ssh0gt416x6a","z5fq+D8emqC/PN+xt2geLb8mLOg/qvAXnxM5TtJRMsc=",439814211377988865,8289486741928857462,-1511757859820086793,3988708557345474453>()) {
                  case 385811989:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private com.yiyiaddon.l.j.g a(String var1, Setting<?> var2) {
      return new com.yiyiaddon.l.j.g(
            () -> c(var2).size() + "", () -> f(new g(Minecraft.getInstance().screen, var1, () -> c(var2), var1xx -> a(var2, (Object)var1xx)))
         )
         .a(96.0F);
   }

   private com.yiyiaddon.l.j.g b(String var1, Setting<?> var2) {
      return new com.yiyiaddon.l.j.g(
            () -> a(var2).size() + "", () -> f(new com.yiyiaddon.f.a.a(Minecraft.getInstance().screen, var1, () -> a(var2), var1xx -> a(var2, (Object)var1xx)))
         )
         .a(96.0F);
   }

   private static void f(Screen var0) {
      Minecraft.getInstance().setScreen(var0);
   }

   private static Map<Block, List<Block>> a(Setting<?> var0) {
      Object var2 = var0.value;
      if (var2 instanceof Map) {
         switch ((int)com.yiyiaddon.m.b.a<"sezll9o97w7r5","1mea/fyc8qK6ydwCMredL++dQfV6OdqcJ/T4nGC4ZUY=",6634533207449329491,3252744541407889013,128228741171084252,-990518355183297215>()) {
            case -1243070384:
               Map var1 = (Map)var2;
               switch ((int)com.yiyiaddon.m.b.a<"s2glj6akojvc7f","pSJ7jOuhdBfrOoTOl27aOJ0YPIQUBgveXT4mNYqfWAA=",-2949663579473249631,-2499421247922839187,-8573809597180136875,-2403541496914438392>()) {
                  case -691758579:
                     return var1;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         Map var10000 = Map.of();
         switch ((int)com.yiyiaddon.m.b.a<"s1hothil49v0ij","DNmZGx3eyZe8S7uDxAinlJHdyXP01yuOCd6gC9qu/YU=",-4187010651420214882,-4945109494263147464,5719065225889377239,1833465995299149478>()) {
            case -354285978:
               return var10000;
            default:
               throw null;
         }
      }
   }

   private static List<String> c(Setting<?> var0) {
      ArrayList var1 = new ArrayList();
      Object var3 = var0.value;
      if (var3 instanceof List) {
         switch ((int)com.yiyiaddon.m.b.a<"s2c5ho01siqjha","4GZxzg7uRl/MJxlexDBOzgTe9NFisIpG5eZfWUcSqcM=",4757869081375594703,-6192307916106549253,-6827571589526463099,-8669571833770015553>()) {
            case -1550865940:
               List var2 = (List)var3;
               switch ((int)com.yiyiaddon.m.b.a<"s29kz9arejq8pk","jLgpKWP7LS7K4R3tiHRTKA+p33YNaAl6chkrXbacinM=",4842313040912889865,-8009996334554740494,-7269896528638340990,-1340753427369328400>()) {
                  case -986402417:
                     Iterator var6 = var2.iterator();
                     switch ((int)com.yiyiaddon.m.b.a<"s1be5z3yw89c27","7MN6ZakcrKThraOCQhYlGiq8emUDH/04GUYI4mt0Kcw=",774692630930358936,2430209005813158615,-857944458940369319,-2372687095395056645>()) {
                        case 346886232:
                           while (var6.hasNext()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2u2gm9if5y65v","VXBYi2hK4uTypsZxEc2YFWS9eqC5eTydh6z9gs1dVA4=",134404751233884601,-7918451274406317459,5864529992708678473,2780971644258561197>()) {
                                 case -1101270874:
                                    Object var4 = var6.next();
                                    if (var4 instanceof String) {
                                       label29:
                                       switch ((int)com.yiyiaddon.m.b.a<"s2z5p8pir2kpvv","0y6wdxYpC8la/0IXd5sIIPGxIHN8r/CIww7ePHAWSUk=",3598514139348724988,2013952048158913406,-1086594143624290451,1074272849823864559>()) {
                                          case 1331228162:
                                             String var5 = (String)var4;
                                             var1.add(var5);
                                             switch ((int)com.yiyiaddon.m.b.a<"s2shsdy18vt2p5","LK23saMAmefHpbR28BZFgLGUYCHnFe/ZnEI0G0Hmplc=",3472566054376442660,7269061115612322905,2238342271077474862,-8139444752876982982>()) {
                                                case -891027635:
                                                   break label29;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s3e2vbg8hrqb0w","iSnQznThwcpjwhwszIAEEXKRwAvjWeTqIUvlDPh20CQ=",-6799162212112210026,-9165338705162563879,895559064714651393,-1685867947125391292>()) {
                                       case -1414321581:
                                          continue;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           return var1;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return var1;
      }
   }

   private static com.yiyiaddon.l.j.e a(Setting<?> var0, Enum<?> var1) {
      Object[] var2 = var1.getDeclaringClass().getEnumConstants();
      ArrayList var3 = new ArrayList(var2.length);
      Object[] var4 = var2;
      int var5 = var4.length;
      int var6 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s1qwwl8lg4aly8","FI2hse9QjLNHvQDywn1ooRW06x7J8p3E4aCw03OuRGY=",7342158437336019003,353431176024402551,-1644078172694135732,3572957997433647841>()) {
         case 1209585778:
            while (var6 < var5) {
               switch ((int)com.yiyiaddon.m.b.a<"serd0qzrlrgo4","dLQ9Dz6r1LpWOKjDFsEkVh2qhuQU+rzqEUme81Rruaw=",-8176897660246715094,-838018024480967086,4389543391981708294,9154021452782328523>()) {
                  case 307610867:
                     Object var7 = var4[var6];
                     var3.add(a((Enum<?>)var7));
                     var6++;
                     switch ((int)com.yiyiaddon.m.b.a<"s19wdgz9nu4c2","grmUa6/I2ADe5fG/9L4nF9Ld5t05RN60Z2VYvoI4JoI=",-4912120771522126214,-6825193020912123280,7742682559630050755,-3123459332043810381>()) {
                        case 499020973:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return new com.yiyiaddon.l.j.e(var3, () -> a(var0, var2), var2x -> a(var0, var2[var2x]));
         default:
            throw null;
      }
   }

   private static int a(Setting<?> var0, Object[] var1) {
      int var2 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"sl6abfu1ja7ug","Ihv4veAYxTc+E2nFHmYGKPpI+pROHykEsCG6BTEqQtA=",-3073432359246988273,3872955439411194172,8871979586553524565,6939847069671040459>()) {
         case 541435043:
            while (var2 < var1.length) {
               switch ((int)com.yiyiaddon.m.b.a<"s10365tcq2qfav","az9fIPlVsTX/cbXrQiIpSV3SnziWgRAGV92j2X6cffU=",-6980715942930340548,-6710533096916913469,-253386156534452942,7505020193559764142>()) {
                  case 1956536768:
                     if (var1[var2] == var0.value) {
                        switch ((int)com.yiyiaddon.m.b.a<"szy6or77qm2lq","FPzPuDzMZaxXFtmEQeHEIKKAycVjl54tLFoXeZ2JqSk=",-8118542407019453417,-1089634488964445686,79102713689055578,6685923462654908835>()) {
                           case -1566322479:
                              return var2;
                           default:
                              throw null;
                        }
                     }

                     var2++;
                     switch ((int)com.yiyiaddon.m.b.a<"sl8e2jn2em82g","We8l5X08tH5sGAMOZqNUNS4dHWIruihXDyrfC3+PuJI=",-1550965905730073430,6443934233804433449,-305097256632919708,-355221620856096093>()) {
                        case 323557950:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return 0;
         default:
            throw null;
      }
   }

   private static String a(Enum<?> var0) {
      String var1 = var0.name();
      byte var2 = -1;
      switch (var1.hashCode()) {
         case -434150460:
            if (var1.equals(
               (String)com.yiyiaddon.m.b.a<"s349vrv8467wg4","lmoMANVV9ld3wnxSCPzUkX4xxMLTnwWgtiuOXHUaTAtxf9bw7Ej3SqZyPF4PJipB",-8860063987123255130,8192292535134985486,2718339386274202629,-4146061644503817995>()
            )) {
               label70:
               switch ((int)com.yiyiaddon.m.b.a<"s2i77zl9jcvdll","p+BpxyCIRnRcClANsGXtrwXy/yOnDPHiqAMFtcaAInI=",7655839736123768746,6725763085360439450,3016708568739736608,6230168216581551855>()) {
                  case -788941919:
                     var2 = 4;
                     switch ((int)com.yiyiaddon.m.b.a<"s24yrf550tjk01","OT1LPLP1oRL+r0C/zwxnKEv+xoRpE2tMuSYvJHj1hg8=",3634755936356012480,-9185609804527254137,-7100532917073057067,-4892853198229906448>()) {
                        case 1477804511:
                           break label70;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }
            break;
         case -72893764:
            if (var1.equals(
               (String)com.yiyiaddon.m.b.a<"s2npsd0p6sgiay","awrbwY1ctcTt/XnZPIc1WoJKIi1HgCwDULL6j7aU4zgXMOaugFvKgLwirTQF1YRgyqTP4Wo2",6253522288223427905,-3029201009711886244,7941722483091109048,4258827967762813532>()
            )) {
               label76:
               switch ((int)com.yiyiaddon.m.b.a<"s1b55m9oltmvdp","ehV3YELiu1r1fs3ACJAL9ePyjBWCDGEvZ+0wHOraUl0=",4088442357702299240,8686290674831033670,196399333496146623,6167499388016565090>()) {
                  case -115467258:
                     var2 = 2;
                     switch ((int)com.yiyiaddon.m.b.a<"s2sx2xbgwhx1s","VywodreSYbrmCJ5YmVUFBE3NobXh1RRVp2vVDF53FWU=",-4066826803400396265,1144944974860902709,777921989258399603,-5015768716860561308>()) {
                        case -235907730:
                           break label76;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }
            break;
         case 2402104:
            if (var1.equals(
               (String)com.yiyiaddon.m.b.a<"sw3a3618vsduj","dZMgaOyvxjZg9qAWet1bfCkKW+5FCMa2R2lhN8kWs3kgO1Ue",-1313280003984990065,6480399442039048698,-7598138793289651391,-133389541523052406>()
            )) {
               label73:
               switch ((int)com.yiyiaddon.m.b.a<"s3kyn2n17texyn","4AzetrR6mx2DddRDdsqPdh+hS0fgslTYhSKjFeMCWUc=",-708392925862180049,-5421523786082410511,2369369723028656213,-3958279864960102577>()) {
                  case -1806187766:
                     var2 = 0;
                     switch ((int)com.yiyiaddon.m.b.a<"s3n7d2sekb7esu","NKryiJMMqNqJTO62OZlePNaY1Lgmi3AYpGaqUAL/hmk=",-6427389427408640729,-3589539894996450326,-7300213746628481542,-4760769846793022165>()) {
                        case 1555150318:
                           break label73;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }
            break;
         case 190066013:
            if (var1.equals(
               (String)com.yiyiaddon.m.b.a<"s2bu1empium1uh","c7syewvyS2fs6G8+rbBCRUXM8UxkXOtkXwZWIOj0pkRV/Q6qzI9ZYK7kxND1ppMo",-4046679365233386386,8167899293301431533,-7665927848024084265,154686273089336294>()
            )) {
               label86:
               switch ((int)com.yiyiaddon.m.b.a<"sx34swj6nn7uk","e9TYX0GYz0XdzJ0KQvMs08K+13Npn4XH+QV5aOrKJD8=",3816531671122757973,6873897517074527572,-2134946841257593192,-4487257187198061885>()) {
                  case -1786750529:
                     var2 = 5;
                     switch ((int)com.yiyiaddon.m.b.a<"stdjnn9otsiev","KhFtRBx+zyHZM97OhClTUxSCkXDvDI6K7lEwuU1hupk=",4264317740777077066,-1482268345063219463,-2520951641561574697,9149884910281692415>()) {
                        case 524180329:
                           break label86;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }
            break;
         case 346275008:
            if (var1.equals(
               (String)com.yiyiaddon.m.b.a<"s3dv2815qjk8jg","WjPMVuTK37jFzqWziCpi3qEr7SK0WTh9b4B5klANkktAzXbXc10JHBQQQgtjPuXOKTMDUMC3QQNhQTUx+VijnBQy",3077563638213385769,-689876464485356711,-6092471490404682723,-835002583313700984>()
            )) {
               label80:
               switch ((int)com.yiyiaddon.m.b.a<"sjbfj4z0t4xtx","8gMAAqnZy+BFe0FeHo7+Je3YGOOeSe4ihwrVRgwKNK8=",-530886680734017022,-6294748964112606499,7990239246298737545,7052757866916210229>()) {
                  case 1213469027:
                     var2 = 3;
                     switch ((int)com.yiyiaddon.m.b.a<"s2xgof1x9uxxyi","laozIrWh21THvm1sKni8rdjx7mRWxsBHQwsH9dcWlVE=",4279151264192114117,-8840574378548558844,-380291227432618820,-272613907584524685>()) {
                        case 209987830:
                           break label80;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }
            break;
         case 1383122148:
            if (var1.equals(
               (String)com.yiyiaddon.m.b.a<"s1ltbyeplzp685","hZaZy10ERdbwr4/imIeWIUzQq6ese+4vWeL749rDlf9Oyxgg+RlXpSBBd93z0FKM0msfcg==",-7371427527733640749,-3920479588006930715,1207788587169562739,-8632351673223407605>()
            )) {
               label90:
               switch ((int)com.yiyiaddon.m.b.a<"s1xknx1ddjwgh5","7AX/OoTBqYKPpF10x/vH+38mFk1mcchR+PjUq7oEqIE=",6465618998386037568,-5426149694346149543,497434637294982652,4031296458420277393>()) {
                  case -704833111:
                     var2 = 1;
                     switch ((int)com.yiyiaddon.m.b.a<"s1b2z1cklalvrx","7coxeLWcxDe1RpsXYrMo+FU97+VFlmNm8szGKWhgsrs=",-7356243534544411937,4036692744727068472,-7128554067864556735,1053492503474737094>()) {
                        case -910684185:
                           break label90;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }
      }

      switch (var2) {
         case 0:
            String var8 = (String)com.yiyiaddon.m.b.a<"s373g0h4r1ygjg","+k/QZ64oG6KlCQ2nQ6ctWConXE5CtJ7if7VyWqRa",-6531634421537881978,5843841913892953396,3673488773216784774,-4442108838403826853>();
            switch ((int)com.yiyiaddon.m.b.a<"s3i0t8zeq28fk","cNKH4CjTNkq3mnMz68foF0zj7EBYDVPNmyD4CPHNVlk=",6259947950967820247,-1187701216377629147,4214942333542539225,4913308423045388097>()) {
               case 684871235:
                  return var8;
               default:
                  throw null;
            }
         case 1:
            String var7 = (String)com.yiyiaddon.m.b.a<"sxhyu23uxbuoh","7tksf4g7MQx+5aw1SIODkZtdMY5ylDREiuLR3j+ri5XfinqnCr8nBmR8",8032064686317360992,8059732009117797242,7084956671881248993,419165867609295290>();
            switch ((int)com.yiyiaddon.m.b.a<"s2g65iarpads6g","KjbQCbPp9+7VKImRrUjvi8iDos5Xr6OXdRH2nQ3nD/g=",7221839630363318978,730031472981782063,8463868062294699658,5714705055789570213>()) {
               case -91647897:
                  return var7;
               default:
                  throw null;
            }
         case 2:
            String var6 = (String)com.yiyiaddon.m.b.a<"sp5z8s0meao7u","+1cfqRR0RIM/oXUpKSjOEcpE/ZNtWPuIoDB7Ujd2DDgHQNQdx9PAA3Mi",7310405430327380678,-944601592435805757,1469609063134804077,-7759960580351086677>();
            switch ((int)com.yiyiaddon.m.b.a<"s3mzfb1ucktz5o","JYlzw8ctX4QHIL20UhucwmbhS0nzGCC0F852YBUcUuU=",354118361577067580,-1764296861844123125,3499012094468727382,-4856946087771827834>()) {
               case -137761858:
                  return var6;
               default:
                  throw null;
            }
         case 3:
            String var5 = (String)com.yiyiaddon.m.b.a<"s2r611txg5ge5p","8SXkPyCrbUeE3g9+D5y7FfmRoYij605QIfzpOXkJE5KsYNHbVQCj5mh3",3668389292585365863,-6089977960882678709,8239704519544964808,-5472388379529796194>();
            switch ((int)com.yiyiaddon.m.b.a<"s2mwkfd6yeujl3","5+bqIwIChuoH+7+v/BrqWvecv0oSy+9tEQou8Wwhsvs=",5749260644779496062,2671068451617879829,6179006270817266842,-4888784667555725393>()) {
               case -204423863:
                  return var5;
               default:
                  throw null;
            }
         case 4:
            String var4 = (String)com.yiyiaddon.m.b.a<"srvs4wg40cm2q","BiaAHTTTM70yqpSo2SHHzLvlFsdvSk2qGiGPJrN7q66xXNqR",-8187897959416803014,1658859624983963066,52834955241162966,-4265833693638284256>();
            switch ((int)com.yiyiaddon.m.b.a<"s19zweo7stmh83","M81hoETJR+gghHUgV0wkERwqi55pOTmDh0olPdGBXv8=",-6250261888992043096,2517420612390722166,-1299994984062741610,5963793844676234389>()) {
               case -1025900271:
                  return var4;
               default:
                  throw null;
            }
         case 5:
            String var3 = (String)com.yiyiaddon.m.b.a<"s2kxmpg64he3h2","FOfRnLtGKVCIbEJbNuTmKfwBKV8a/YKUVgOSz/XiydIb4vXG",7097130466274809288,-4406688170576694196,3098510382829297653,-8534447190764641856>();
            switch ((int)com.yiyiaddon.m.b.a<"s1q95l8o5mm2rv","XROpOh4w3Y9Tcia9FYxUiwKDrer6V8HSSqo9CHAtaG4=",-2977675689652854439,-4017683025926387579,-3096322088656439361,-2216998967772838835>()) {
               case -1275617411:
                  return var3;
               default:
                  throw null;
            }
         default:
            String var10000 = var0.name();
            switch ((int)com.yiyiaddon.m.b.a<"s20hnw4qf0sud8","pNGTUQBqzOGzlD+MVCRxkIjUuSLG3Nd2T4ZzQ8zGz0o=",-4640941681449411008,198368889526498515,175146869848171285,-1862471514283587464>()) {
               case -1632004994:
                  return var10000;
               default:
                  throw null;
            }
      }
   }

   private static o a(Setting<?> var0) {
      return new o(
         () -> {
            Object var2 = var0.value;
            if (var2 instanceof Vec3i) {
               switch ((int)com.yiyiaddon.m.b.a<"swllvujjjyil","5r6oXK24KDHMCpFrhinNMHG2P1yW9uH56ZcNmEGQVKs=",-1688168711581817082,-7520161091484882813,-3164186281025947024,6382573798908983527>()) {
                  case -1949818547:
                     Vec3i var1 = (Vec3i)var2;
                     switch ((int)com.yiyiaddon.m.b.a<"s2bxwmnzrqc04j","TMdcSOigdSjzeWsXZM2/J06v82Qqg4l/sEJz6vm9xkw=",4734251400899146377,4926455486593460861,-6586114302990201224,-6407572392741911057>()) {
                        case -487502478:
                           return var1;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               Vec3i var10000 = Vec3i.ZERO;
               switch ((int)com.yiyiaddon.m.b.a<"s2mcba7yfi1cxb","tS5P8ErKCfCf4jUMrTRL7R7WbbJZMkQ1eM4CVApjc+c=",-2548548930033845934,-2405417462072576351,1646097132472485781,-5119221240164690168>()) {
                  case 1167248605:
                     return var10000;
                  default:
                     throw null;
               }
            }
         },
         var1 -> a(var0, (Object)var1)
      );
   }

   private static String a(Object var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"seopltubsfev4","m0fmnktUzJWYR/hAHq5T1ymjIYYS9rfCAe6FTvAq1fo=",-2814049441669872648,-7296509374999545823,-8942881582145660835,6956599475059855162>()) {
            case 1807435833:
               return (String)com.yiyiaddon.m.b.a<"s1zaubmn9ha4lp","CChn4vsC93ZbyJq6PvQkcN9Ue+tqqVMDHY7j7+DLvQtIWw==",-1199294701207908827,7045819140239219915,2468001887664980259,-803699683778068389>();
            default:
               throw null;
         }
      } else {
         String var1 = String.valueOf(var0);
         if (var1.isEmpty()) {
            switch ((int)com.yiyiaddon.m.b.a<"s1p5rdpeprlgd9","Ie3m7Hko01azgfgAPDNDR7QbfDbuSv7MoT2/eRcn2Ew=",138235626913437500,3977845502999570891,7380787453541379883,-7882125547261939042>()) {
               case 1214505916:
                  return (String)com.yiyiaddon.m.b.a<"s1zaubmn9ha4lp","CChn4vsC93ZbyJq6PvQkcN9Ue+tqqVMDHY7j7+DLvQtIWw==",-1199294701207908827,7045819140239219915,2468001887664980259,-803699683778068389>();
               default:
                  throw null;
            }
         } else if (var1.length() > 120) {
            switch ((int)com.yiyiaddon.m.b.a<"s2dq5z344t8iwn","ekGJj7uyiVmrUqDB+7JUrxL7/ieNG228tqEwM4s4hVA=",458958433406766870,2354823150132173872,8293971353300063503,7496973769951225364>()) {
               case -149454758:
                  var1 = var1.substring(0, 120) + "";
                  switch ((int)com.yiyiaddon.m.b.a<"s26yfu8pdta16s","+u/AuE4Hm9dE8ZcRd3GnPS5g4j73LERxnJ8viYucNWQ=",-1084660104807889212,-7178059301741116930,-7589800811892059588,-6471412664240516661>()) {
                     case -1737773916:
                        return var1 + "";
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            return var1 + "";
         }
      }
   }

   private final class a {
      private final String Ch;
      private final String Ci;
      private final String Cj;
      private final List<f.c> cA = new ArrayList<>();
      private final List<String> cB = new ArrayList<>();

      private a(String var2, String var3, String var4) {
         this.Ch = var2;
         this.Ci = var3;
         this.Cj = var4;
      }

      private void a(String var1, String var2, p var3) {
         this.cA.add(new f.c(var1, var2, var3));
         this.cB.add((var1 + var2).toLowerCase(Locale.ROOT));
      }

      private void fb() {
         com.yiyiaddon.l.j.h var1 = new com.yiyiaddon.l.j.h(this.Ch, this.Ci, null).a(this.Cj).a();
         if (!this.cA.isEmpty()) {
            label29:
            switch ((int)com.yiyiaddon.m.b.a<"shp4guq8xgcbi","/1tVvTsrlJP7gffVrjy76E5OuVSjAoZGs433TiZPTSY=",-8562264493951142993,2071353529823559850,3820663148263872426,2175414546340665234>()) {
               case 237096734:
                  var1.b(this.cA.size() + "");
                  switch ((int)com.yiyiaddon.m.b.a<"sjnnacnvsje5a","fgCCUPA35+BZ6WcW/2b3Ip/U30B4GBPqL7rls2a0kFY=",-1749993409534819842,3758258891066831271,-847499443375626948,2399206466822574018>()) {
                     case 1117406683:
                        break label29;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         var1.a(
            () -> {
               if (!this.eT()) {
                  label22:
                  switch ((int)com.yiyiaddon.m.b.a<"s2gymlply54lhr","IjtjPX3os/VQbxcHsswvtmN9AmxEO+TL/jrLik31YAc=",-5418359934504460856,-3373120313627369697,-2432771595507623941,-3506776279040047919>()) {
                     case -1660189388:
                        if (!this.eU()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s275a511smccnk","5Oozr81UE4XHor6U7Oruc+/qBoR39n1SqedntDnUq3c=",-4881873901377230865,-8618230232276816887,-2735277377443993688,-5272030077913127071>()) {
                              case -1235918095:
                                 return false;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s2nr6qxibbbune","uCJhHqSXLNT3Qch6kpLQc/FJBW8RncKUDXqMRnCWQn8=",1748361584979326860,-8403215113456777968,-19036621234608877,7523640104713597892>()) {
                           case 2002746719:
                              break label22;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               switch ((int)com.yiyiaddon.m.b.a<"sopp49oty8ryf","0cXEvM2ccgEhmKGR5VdtH2aPqdBBtzsXiShec1p+A1U=",-9209288351799338196,-1522449288018355783,366813119168820289,4579659522065680264>()) {
                  case -514050526:
                     return true;
                  default:
                     throw null;
               }
            }
         );
         f.this.do.add(var1);
         Iterator var2 = this.cA.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s177xwaykce3eq","1Tu/seKW3pXEUwIrAiuAVcDB2H2DJHLuJ3zoX0ye8Ak=",3454333659569214768,3075089061819117273,-8061756133142385305,-5933570314927251589>()) {
            case -104907049:
               while (var2.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"snov4efueq7yu","R4AW1UeXkeg73w+uTQUvVZXfgjQxQ6vN0WX1NkyazH0=",4211872601844618598,-1741496603010637651,7809227080947372811,-2173390609744738966>()) {
                     case 326301967:
                        f.c var3 = (f.c)var2.next();
                        String var4 = (var3.h() + var3.c()).toLowerCase(Locale.ROOT);
                        var1.a(
                           var3.h(),
                           var3.c(),
                           var3.a(),
                           () -> {
                              if (!this.eT()) {
                                 label22:
                                 switch ((int)com.yiyiaddon.m.b.a<"s1c2nzqlwcq5p9","x6BkU8eETnoCwe/O/ACEEvkYI5i16aQORUuurH1INNI=",-343234597972459140,-409525753676891551,-6439968242670934253,-5564046996094889388>()) {
                                    case -1325086048:
                                       if (!var4.contains(f.this.Cg)) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s1xffeltkppe71","0+s3b8zTL5n99iqQj1CLNw/zI1vaTStDMeQ4MnYRyNI=",-7864632898471223707,-7485970528335535152,5665668024293712160,8953998840607212097>()) {
                                             case 2012902318:
                                                return false;
                                             default:
                                                throw null;
                                          }
                                       }

                                       switch ((int)com.yiyiaddon.m.b.a<"s135d6cw6oyymr","NATXB0pIvuJYAMmdI5mFcoMUyF3nGCTJj/xr3yxx3YQ=",-8126950019308786166,89632678865389430,-85089328141751288,-3286535581830960637>()) {
                                          case -866789195:
                                             break label22;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"swmmuk59fe0e","jfR38Qq0ra1ThL3n/Kr8eDhzRZTRuFCc2U1qJ0x4j3E=",1153527401531018154,-2983312765186771287,2354919644094661376,-5646651276685310263>()) {
                                 case -1560089108:
                                    return true;
                                 default:
                                    throw null;
                              }
                           }
                        );
                        switch ((int)com.yiyiaddon.m.b.a<"sh1uk7812gp8t","nAKSto6xU6R3gfrfclW3jg+/6GCGhJZ27Ipw3NaY5JY=",-50733776800976335,8624193264899971968,-8691849449333190753,6416668211741675862>()) {
                           case -2075010945:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return;
            default:
               throw null;
         }
      }

      private void jh() {
         if (!this.cA.isEmpty()) {
            switch ((int)com.yiyiaddon.m.b.a<"s3oht1vuqrsu8i","MgVm3rxzKlhJla9rjFW25laiBIQ3TrTUxGnOa5y+p6w=",905634959251853727,3454560687829862242,3659557245192285849,1864092825406811444>()) {
               case -432842793:
                  this.fb();
                  switch ((int)com.yiyiaddon.m.b.a<"s21awjf3ukzgl8","h4tUQs/jlS6jNSCNiss/VCzsXPLQziNwevLx1pjxlIs=",-8596271982555929659,-8057478874911517489,8934313972025198271,6599178066866533460>()) {
                     case 202662489:
                        return;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }
      }

      private boolean eT() {
         if (f.this.Cg.isEmpty()) {
            switch ((int)com.yiyiaddon.m.b.a<"s3soazwoong45k","oEJAAkl0g8qoeYEPTyFnL99jc3Wj6gUF45GLTZO33XY=",6944602875881452366,2167007884668587276,-1476548212241233006,232727063994986962>()) {
               case 1598178011:
                  return true;
               default:
                  throw null;
            }
         } else {
            if (!this.Ch.toLowerCase(Locale.ROOT).contains(f.this.Cg)) {
               label27:
               switch ((int)com.yiyiaddon.m.b.a<"s2pramaipo8rqu","Mw5Ufa5TvmkdLxvqSy8heCOv3MC1s+jU2HWJJEJthqI=",-5264856435765848428,-8970786632178432436,8387840970336238944,2272530872483923513>()) {
                  case 514521793:
                     if (!this.Ci.toLowerCase(Locale.ROOT).contains(f.this.Cg)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3mu977bcxsbmj","N0E4/ZwQ0myB1Z4kEyCUD67cYSXds6gd3wmJyfMOlIQ=",5802537635346783561,-252026621711850099,6741045207141526559,-2994371075617554777>()) {
                           case 1156579605:
                              return false;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s2v4btg8pzbx6l","TBZqIYo90/7PkxHuxuCsY8+dk/WP44iIr4CyWWIJTBI=",4035940358654520103,1454111095329115029,109098326600038371,-7708880867928092215>()) {
                        case -846342284:
                           break label27;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            switch ((int)com.yiyiaddon.m.b.a<"s35ns15t7bks8q","sCbraj1FvHYdfCIa5qwXIFKMgfbFj+zHVeti+8Nrx4M=",-4227055106348068339,-421953519493919090,8506344614879852651,6894086809165033875>()) {
               case 130290053:
                  return true;
               default:
                  throw null;
            }
         }
      }

      private boolean eU() {
         Iterator var1 = this.cB.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s2lmm6zjqnbd6j","mC9ZaAwEbu/R+S2rLB7EpTmgx9ariCUWzuejxdwLP9Q=",910252243417906588,6613158587928520244,3819639319851690109,3368985227503998638>()) {
            case -985674997:
               while (var1.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1fxmcd8ftu2yc","KCeK0P8IBI3D3wviO8ZHZab/BKWdblM4JLc1LVEE70c=",111424166076583039,5431690955766099960,5254189178376499561,-4686186368146993574>()) {
                     case -1890703740:
                        String var2 = (String)var1.next();
                        if (var2.contains(f.this.Cg)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2hgjjpofyh9a2","V7CbGoaYS1nsSo0xUuoZRzE6NzPsSjjPbKvOttDOnWU=",-6989203946681436769,-987097408804747533,359070098799637556,5737765356830831391>()) {
                              case -2091741755:
                                 return true;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s18tihohgkuyyw","7SUtaMVKJ2mBDYIu4ZvJx+c2Nwq9W4cJDIODP97G2so=",-7563715015875438309,1798346257920292289,-6546153278146833969,-1778849353149049312>()) {
                           case 2013548841:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return false;
            default:
               throw null;
         }
      }
   }

   private static final class b {
      private final com.yiyiaddon.l.g.a.d Z;
      private final Setting<?> a;

      private b(com.yiyiaddon.l.g.a.d var1, Setting<?> var2) {
         this.Z = var1;
         this.a = var2;
      }

      private void jg() {
         Object var2 = this.a.value;
         if (var2 instanceof Color) {
            switch ((int)com.yiyiaddon.m.b.a<"sijb8ym3ziot1","8iUp3/jofWy0DehIMoZpirSjFz5l5bKGveIXzcYb9rc=",5986087412564092271,4188126217686645301,6602880775693935210,6272725188896465217>()) {
               case -1432218869:
                  Color var1 = (Color)var2;
                  switch ((int)com.yiyiaddon.m.b.a<"s2irxb2z5beehx","l2zOycPHXSMK0/uumNiWO6A4o+yc7sHJ9NqjKAD9Ozw=",168363240842933306,2034211164337975541,4449947665707354709,7305305065456161188>()) {
                     case -295665895:
                        int var4 = this.Z.eh();
                        int var3 = this.Z.ei();
                        if ((var1.getRGB() & 16777215) == var4) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2qqsvx4wr4iy6","+KvdIQCjuIQC3O2JgDKP3z9gGcMJOP+B9lwLZGlxsyw=",-5574328555378154959,2590762290601795880,-5133674052631645616,-739517950517841582>()) {
                              case 1709393651:
                                 if (var1.getAlpha() == var3) {
                                    switch ((int)com.yiyiaddon.m.b.a<"sdsbtrpkcfl2d","2OxO++MKieGt/sEFKSpLhkzNR8d53DFEuBHdGvoVI3s=",3391769954082025297,7056504207845027916,-8705832365897749021,8734200989455955600>()) {
                                       case 1459413585:
                                          return;
                                       default:
                                          throw null;
                                    }
                                 }
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        f.a(this.a, new Color(var4 >> 16 & 0xFF, var4 >> 8 & 0xFF, var4 & 0xFF, var3));
                        return;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }
      }
   }

   private record c(String Ck, String Cl, p b) {
      public String h() {
         return this.Ck;
      }

      public String c() {
         return this.Cl;
      }

      public p a() {
         return this.b;
      }
   }
}
