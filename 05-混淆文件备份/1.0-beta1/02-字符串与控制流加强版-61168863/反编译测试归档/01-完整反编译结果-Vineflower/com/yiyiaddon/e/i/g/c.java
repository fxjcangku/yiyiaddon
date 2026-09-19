package com.yiyiaddon.e.i.g;

import com.yiyiaddon.l.c.f;
import com.yiyiaddon.l.h.g;
import io.github.humbleui.skija.Canvas;
import java.text.Collator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;

public final class c {
   private static final String lU = (String)com.yiyiaddon.m.b.a<"s1rm51eg2su3un","NJLUDADVftPYO/5BP/aZS45jfdUBrmjYWXygQparMAniDetc65Dkug==",-527631769925709681,-7247577105081686570,-1591291013254475536,8960132371924534920>();
   private static final String lV = (String)com.yiyiaddon.m.b.a<"s2ljhqms6g77f4","4UNIt9JNs9C3Vh1E+Fj6v973WVhx9oiJmfuAeIH4k6ev4NtMYgtrIvN3Uek=",3527626995064457126,-7046931058884234487,7978211636556192119,-4584161360385561482>();
   private static final String lW = (String)com.yiyiaddon.m.b.a<"s16csgx3a3njfk","EQjgtApsI3VADY+2eMsM4gJMuvYMMizRyA9S8oBxzS9rNLnV",-7092990582007359268,879980551490751660,1238241871924135224,-4396367813511632109>();
   private static final String lX = (String)com.yiyiaddon.m.b.a<"s6sh72l7ocoe1","KtiIMf8Q9dSpx5nUqPpng7V6pft7/4onDd32BKn7P/50Dasf",7041411427996802432,1053352069095051879,1501965107741782518,-107469699884170615>();
   private static final String lY = (String)com.yiyiaddon.m.b.a<"s1xy0hfp9ge327","4anrAsakaGGnBLCo7wAUcB3CijxpmqFFc9CCC7LYEYcR3j8+uTf1nw6J6OBMdxMqoOBiEdj8P/WGbjvp1u5VDCLlO6Iom0HuD93OkQgq+zvA1ZwGuLa2tCTtmHL6RzeCnsCWlA84yhiqHRCNVXv+Nd198fUnx3Mn0Tx/0T3Gtyv1V5F/sxWVZC6t9PPsP5JCXZMAFo8YiDw=",-1929124510414028083,-4488134616112017289,-7599400937081641009,-3446268612628014829>();
   private static final String lZ = (String)com.yiyiaddon.m.b.a<"s19yj0iqj89r9g","IUF2RNyeeY8ecgPU6qG5fqa9tUCc3Vmz9LSHMVAW8NGBfw==",8736180866277804132,3138097587839144114,4600390267187083944,-8746763930802480762>();
   private static final String ma = (String)com.yiyiaddon.m.b.a<"s21pttmlu2xfmt","WnZ1LK4/giuIYpkAHIO4ezpYU9jyzcaM135LDcjVLP0vq4SZVuwIZttLg2QqGg==",5598622779141708545,5695842180374711235,3031861084079355404,-3102665259979703039>();
   private static final Collator c = Collator.getInstance(Locale.CHINA);
   private static final com.yiyiaddon.e.i.a.b b = new com.yiyiaddon.e.i.a.b();
   private static final List<c.b> ba = List.of(
      new c.b(
         (String)com.yiyiaddon.m.b.a<"s36acw0534pyqo","igdB0XI90FzhfqUWvlP74/cWwuvIHz29wL/0aFgpySoh6cM7",2147273976932472354,1199478212508588172,4708957860218833604,-8313156365689297850>(),
         List.of(
            (String)com.yiyiaddon.m.b.a<"s1ds397l8wux42","/ec6bl18HcsBFutTcU1INvsodqjpfg8t/gI6z3ODt10pBGciWPw/KJFUd/OukA==",-8563209664893893655,7043677136054787091,-2154968281273811835,-5486497480321878620>(),
            (String)com.yiyiaddon.m.b.a<"sdny0ihudsi2u","dio8f+B1mmXECAziOZlHVtyf+XxlNxX9/OeTnXeEl3KODPtdkYs=",7821756724587165277,3635700542258396549,-8751659196776257598,-5561086154086480721>(),
            (String)com.yiyiaddon.m.b.a<"s66su7j62fedh","2obtAI+9sdd77UmnZVYU4HRC0AkPvwYEfSEMsKoEPYoyyLoPiSCAh2dS91cFOLNokWSnkdf5MVBfrglporIsvQ==",-8822841414752366430,-3259988628414878479,2612235291342096589,-4262927763604490964>(),
            (String)com.yiyiaddon.m.b.a<"s3lv83bgfmmr0d","fKlAjiiswUikD2uDXN3DbfUMPV/jdW8u9uyGZw4/2C5lmve1RLU+VL4esYj0jA==",5532395525644980563,3208077494936191918,8866633475016876584,-6570729000033250428>(),
            (String)com.yiyiaddon.m.b.a<"s3s491dzmvs5s8","qzW4hP+KWKS3vWIU1tmEkiqBZ0e36g3mXv7fa+XA6k0YPvMO80m3t/xKDqFxTF0i6Gw=",8303875038462823447,-5116578185195736496,7625461775268536853,-2538280262551785742>(),
            (String)com.yiyiaddon.m.b.a<"s3jltv1nsi8ce","7zXxJ5IXQqB8nXhhnNSFa0dagTpjzV4MmCy3U/a+nJtwXmOrQd5tUlhO",3028309023092338377,968041582225214639,4738786208053269861,-5688953931851490181>(),
            (String)com.yiyiaddon.m.b.a<"s3ug84u28l3ua8","59czzfpTOwk13jtBqrb9D85IBA/wYWPOf2beKkuu/Ua5jBpYyEJB4ooYz9WSUOrJ0izhmK2a",-2636125280279453573,-9101336685953783399,5706566006227196874,-6295240029162218131>()
         )
      ),
      new c.b(
         (String)com.yiyiaddon.m.b.a<"s33u9hnj6mn8xd","SUs3KIUga6VVS+0ik65QSAVpRjmAvp4828ocuGrNDQjDupFa",-4332417003607234780,-5413563221095942492,-2179706727810324309,3826971712108347133>(),
         List.of(
            (String)com.yiyiaddon.m.b.a<"s2fxml2qy3uk2l","SknyHzNPOR4JyIzeaLS5Mi1HWUxPzp6EI27NLb8vyWmrByZSpfDLeH+IFIUa1taj",-6009444387849928876,1440035224781700794,156831866437908247,9028791834431792807>(),
            (String)com.yiyiaddon.m.b.a<"sukgpwhl5m89u","JXGq6pG2aWgkywT1glaDT/JOfAE0aumezpj436zhDbd2i3/wLdDXUt65vepjBk9n",-2715805352856207628,7751560363064937842,833874572947908013,-3474459223943175179>(),
            (String)com.yiyiaddon.m.b.a<"s7bihttdw3b7x","mZiFittMsE52H5ge6/XvXmDFI/gIEyq7Bsqijjn6Y0Hd6gyvsEq6jeBU",5202217290103164207,1944478214548264180,8462247932311780711,-3965923841838743546>()
         )
      ),
      new c.b(
         (String)com.yiyiaddon.m.b.a<"s1ztxdh6zx5km9","B2ET+wuvR+IxGfKL75fQNAUFTTPIylgBBXO1AoFWDxXu0GQL",3567035452374909400,-2704293610077555043,1944146245077891498,6612621057114557981>(),
         List.of(
            (String)com.yiyiaddon.m.b.a<"scbkspu0wvpsq","DF7gwObRxiAxwFZe7dtkJHsInbEwxxfRvt6QQRi1OG4Qwefuv8M=",-2379592936729929106,-3242370870435516787,7132451977650063025,-9036756294377775601>(),
            (String)com.yiyiaddon.m.b.a<"s6obprllhtjsn","X8ERlvZjrFud2tmYLdAxvAOco4SGOy/ytCrHqGSe/ayL2tNqMJI=",-752479374349313414,-8144431721745675964,-5637915810882458161,3316440198547274645>(),
            (String)com.yiyiaddon.m.b.a<"s3mwulggy3r8h6","vzG07X8W3Q8Gg8Fpq63995M4ldY0TlCv6+9hELUz7QFS+il50QM=",5391117520009626770,-7409565326826777610,9023593343935964786,-5393005626221568932>(),
            (String)com.yiyiaddon.m.b.a<"s39qk0cej6ow2v","qChThvKrgQRqa+4+HJKjm1jMNlRf6jwWDUkG1QiG+wwMiniuFG5uftWC744=",3867824084568046885,4334213395046625803,-1815052456776096014,-1194172953499207147>()
         )
      ),
      new c.b(
         (String)com.yiyiaddon.m.b.a<"sxbrpclw1l2sm","cWoumKHQfUp/Bpe8yhrTirRlXeaX9/MOatcoBFjY4w/gkw==",-6662330491370723399,2700545852090156552,3559683803705639397,1627824786266538254>(),
         List.of(
            (String)com.yiyiaddon.m.b.a<"s1czce5g6sl7v4","qomsOmFohzWK3AsDDRntcyoAnjSI6Ap+mkXfis/OJGAtwLBkXxQwtMjsjL4=",-3532106641313959386,2135198995960914835,1597342454882210311,-4700157615426244601>(),
            (String)com.yiyiaddon.m.b.a<"s2mxysgqfnlvp5","21PDM8NchXVrs3F63Gc4qttMdmyU+IfdrmL8Ib3CtAyVSofo0HWvFXCpoUOAGQ==",8543291332590204823,-2640803557115036465,7123123956223647464,-2190391997900089767>(),
            (String)com.yiyiaddon.m.b.a<"sp0y4i8hjg0vl","Ec4VnMoTUb9lWymBlG/Q8yUuUV3y8eStkdPtBemN2NpDdHBINuEqR/tTITIxVdMFBmCI/w==",3136914408460399291,-6526117735901920207,1358077973459306367,-3550132819694906390>()
         )
      ),
      new c.b(
         (String)com.yiyiaddon.m.b.a<"s1fiter1owbjsn","zj3ZTxgNa3/bZd9qoF8fSDpBorTLq2esyx26OaVlO0aZCzkx",-6455713986506663773,-7895547124585699146,-7599695684944335763,146055785974256652>(),
         List.of(
            (String)com.yiyiaddon.m.b.a<"s2yer46ttfl479","xWJyj2o6gjHxCnnj9nwmZn5cyqUkmuJN+wbD7OQxNcAPPyetpmSpVdVNyC4+eH5o",-3974458235204288063,3471061860855245797,-4783686008889491615,-1988352956138675002>(),
            (String)com.yiyiaddon.m.b.a<"s1dugh1n20q1de","97g82qC2Y9IOwT65ZXqJWmwMsxmEdLz0FF2uqPV1djH1tAj88sZ3kLi0WunRPt9LlAfEsyxFF6M8gQ==",6806613929242256987,5646442946869631878,8464527973081348681,6493520818575512692>(),
            (String)com.yiyiaddon.m.b.a<"s3byaaaszo12km","zUsxn1awDWCYOQ3wqB7ZT1+LvDPrz7odDqtyonKCibu1Im5NNvabuCu+/J9NGZOSQ6tjM+9/ttJT9rli",3884137445328400545,4258142426508029580,6341109449405440493,-3585952666233353943>(),
            (String)com.yiyiaddon.m.b.a<"s3ajsvcm3gr46r","oc3gmz+50qTOj8ZROY56jglK4IRk8IiqbqEl4NRYA83fwIsaa3aiu7Gc5UgvAo493RNnSCP3nX1zQDvcCfAgQ94O0blHwg==",-2056910547737643307,-7784411508745495602,6391084237347790569,-7785869425236334658>(),
            (String)com.yiyiaddon.m.b.a<"stpeqrv1k2lmq","b0tRDFBuxhSpV/pdkNMguh8Hx2Ir949d3M12gJsWdupEri75cTzDV3DgtURirIIcewu9t9zpMEQbjw==",1003200285257504336,-8701880282082927604,8314797563628758705,7528065984050819638>(),
            (String)com.yiyiaddon.m.b.a<"s21gvp8qli9e89","13sFoeSn0Vnj5ltdX7Hx9fXT0neoNT5sk3ZbuNYU1P2tamF8U2Z7dA==",7996997211603544301,1532043125672785212,-2436337798328294607,4903764181589975885>(),
            (String)com.yiyiaddon.m.b.a<"ss57lj7rgyjvm","gqPYvTq1Z3ZMoPxtxVrmcIb2NHtubtHKG/mBc/d6BdT68eQmLuwE2JQaaNJZo1iP/sw=",-9202524066146335820,1010048825495393134,-1300371371310879870,-8742183109550233906>(),
            (String)com.yiyiaddon.m.b.a<"s1t9omn1wu24ht","od/3cYUdCIRVplgaSTz23cYHHqE+fcAQefOYq9I4wCM4gF78NJdeEoT9hwFEkfpiAj9KfbIc",7804277205304705963,-101664906551161015,-4122962250348686393,-1844395054513288879>(),
            (String)com.yiyiaddon.m.b.a<"s14d7l4h1u59zx","hjEgV5aHK6dXXqDw1qzkRGcKUW87mYuwhCvVWVSzEyXmoBLZAq9uxfFj+tS2g7hgEzF+FtVG",4381609161863787890,-3548077755237305294,-7782243802491560391,-315694189265177679>(),
            (String)com.yiyiaddon.m.b.a<"s2ne9ezyglvz1f","5OADy2q+PwxQPA3WbGI3OiYIj/Mf3QlSoLeFPI0F8ghxQCJ1Skh8n/0v5I1HzbssdYsJ1w==",7542492932548616536,4820351736327305838,-407619538357513609,-1886457624716230094>()
         )
      ),
      new c.b(
         (String)com.yiyiaddon.m.b.a<"s3gf58inhpodoz","pDNN2yF4971ZAOKI+PyNvkSnRKQk3Dd56acU/25uL5zvPL+F",-2613491973829391349,-6660090433807493171,-1526227981220548168,4226661956581808026>(),
         List.of(
            (String)com.yiyiaddon.m.b.a<"sekvtar2yiv4w","suI/DtI6C6VS3KPuwwiZ19yjqNHMmEOT9mO45Xsbkay2+WnGcu5k9VDPkBE3TO9NBtdoJPB/MbZGvw==",1852147795137844376,-172067771882184366,1918783143820898288,548232020675117871>(),
            (String)com.yiyiaddon.m.b.a<"s1ragdjqyka01r","CfUd/YFZ9hCs2iJ2WOYiu0gfdRtFEvAPxtYnrkeyu+M48Oox",-5557918115222050657,7230120895928905064,4908473998763663403,3268349491915182917>()
         )
      ),
      new c.b(
         (String)com.yiyiaddon.m.b.a<"s2fzm737p4g7im","vXXc4lPWM+jCDjxS17/DnjLCqOVfFlBfn5dmShY0A/ezPnSW",836707061511648019,-8188417475292656040,-9089685208037711413,1364157596424593147>(),
         List.of(
            (String)com.yiyiaddon.m.b.a<"s34ot4ipttvbve","9LwoTnfNcbHbdRUWkKshMJy2f/dvclFnVY/3/TPKdNewzUCf7TKDRAw8",-1955329446254799511,3540897653202385949,7910347655373601255,9056319278478133757>(),
            (String)com.yiyiaddon.m.b.a<"s1jdudcru3o0r1","/VD78hZv3CRP1eTvCJtIg8F49xxsa99MofZIpZrwm8bfft34av+jvGWbsYMikwll",2773091184197516229,904169960981040694,-2943574075134199313,4379900565737134919>()
         )
      ),
      new c.b(
         (String)com.yiyiaddon.m.b.a<"s14yp22dew1ktq","C+NET3ky7Ol6hxF2+tMtKD1iJK2tbXdv7R8eJXO3ZOp9H7GADJ8=",-2466750559666795029,-6426699724216795039,-3087506686325694503,-7471219314908926706>(),
         List.of(
            (String)com.yiyiaddon.m.b.a<"s2j96dj3vhxhvd","qiTA+aA+ClO25j9o94RZ31uUz9oi69WFkrYqXg14Y61faZEBhMAiczWv",3782665532065493311,6752374771647504910,3237756108731699353,2520452399700182685>(),
            (String)com.yiyiaddon.m.b.a<"s3pnpm9xgp167g","IE6OPOMx37qqeH7cTln6rav3wnaCQKmo3W080xSAwGxsQxIcJ2ze8M8lUhg=",4472067178201785093,-3511826933099706416,8194186702858254694,7467603061022335829>(),
            (String)com.yiyiaddon.m.b.a<"s30bgqqu8eout2","/FP9Oh8OWXY+VNLmDwUdd5LCkgM7WaZq0pO00NMcESiltxh/aWozFByN",-95510635908822509,-2272677909571863467,-2244650639156214991,-9041511229541686822>(),
            (String)com.yiyiaddon.m.b.a<"s15y43rb0v23v5","/yIKl6C8JMHHp4GZ2JfAXW/jjwWSKgC6Mdm5pxieG7ZlovOv3LivIhkw3s4vK2zA",6983684308160671357,8386349544513589222,-6469680562082586800,-5962093633077118690>()
         )
      )
   );

   private c() {
   }

   public static f.b a(com.yiyiaddon.e.i.g.a.a var0, com.yiyiaddon.e.i.a var1) {
      return f.b.a(
         var0,
         () -> (String)com.yiyiaddon.m.b.a<"s6sh72l7ocoe1","KtiIMf8Q9dSpx5nUqPpng7V6pft7/4onDd32BKn7P/50Dasf",7041411427996802432,1053352069095051879,1501965107741782518,-107469699884170615>(),
         (String)com.yiyiaddon.m.b.a<"s1xy0hfp9ge327","4anrAsakaGGnBLCo7wAUcB3CijxpmqFFc9CCC7LYEYcR3j8+uTf1nw6J6OBMdxMqoOBiEdj8P/WGbjvp1u5VDCLlO6Iom0HuD93OkQgq+zvA1ZwGuLa2tCTtmHL6RzeCnsCWlA84yhiqHRCNVXv+Nd198fUnx3Mn0Tx/0T3Gtyv1V5F/sxWVZC6t9PPsP5JCXZMAFo8YiDw=",-1929124510414028083,-4488134616112017289,-7599400937081641009,-3446268612628014829>(),
         () -> var1.a().af().size() + "",
         List.of(
            new f.c(
               new com.yiyiaddon.l.j.a(
                  (String)com.yiyiaddon.m.b.a<"s16csgx3a3njfk","EQjgtApsI3VADY+2eMsM4gJMuvYMMizRyA9S8oBxzS9rNLnV",-7092990582007359268,879980551490751660,1238241871924135224,-4396367813511632109>(),
                  () -> a(var0, var1)
               ),
               (String)com.yiyiaddon.m.b.a<"s1vpkn5hodubyy","pGlyH16Zz4HM/mqlWwdt5y7BPkn7TYv5XsQYLd70M/yBqxAGPAsuc20qfshL7m6sR7g=",948415584707803659,-3408380654620175929,-2813186917772673783,1829663391631920966>()
            ),
            f.b(
               () -> {
                  var1.a().i(new ArrayList<>(b.af()));
                  var1.L();
                  var0.C();
               },
               (String)com.yiyiaddon.m.b.a<"s6sh72l7ocoe1","KtiIMf8Q9dSpx5nUqPpng7V6pft7/4onDd32BKn7P/50Dasf",7041411427996802432,1053352069095051879,1501965107741782518,-107469699884170615>()
            )
         )
      );
   }

   private static void a(com.yiyiaddon.e.i.g.a.a var0, com.yiyiaddon.e.i.a var1) {
      Minecraft var2 = var0.a();
      if (var2 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1c6v5i8ta6cfo","zQIKUoRjX0WMRR+NbvV7FFjK4qDgDUAJZgioZdaTahY=",-5802901516790270535,-6061106150534683908,-1505741927052438470,1559721410723369>()) {
            case -1658079042:
               if (var2.level != null) {
                  Registry var3 = var2.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
                  if (var3.keySet().isEmpty()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3ftc451x8p5em","m45TwceOk4TqwZBK4A2aHfo0agP/sEg5UYdvQxhlMjg=",6786667679965457860,9050204395371139398,-4910085823905037395,-5068667246874649507>()) {
                        case 355377025:
                           return;
                        default:
                           throw null;
                     }
                  } else {
                     List var4 = a(var3);
                     HashSet var5 = new HashSet();
                     Iterator var6 = var4.iterator();
                     switch ((int)com.yiyiaddon.m.b.a<"s1ujr765s6qgnd","WTV26m08loWl+nOPFoigD9Ko5WZVRBcriUsIDIA22K0=",-8995452679298948282,-7139989325594386132,-8338701508388556257,3250539438777742635>()) {
                        case 1196516662:
                           while (var6.hasNext()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s9wd3a9lo3fip","BCIBm+UbWWUk3cD53+fPBq+jY0Y65R3QowfpeebqjjM=",-1264282645070186530,-9048665963522243061,3415054744801423122,4038596579056881420>()) {
                                 case 293134526:
                                    c.a var7 = (c.a)var6.next();
                                    var5.add(var7.L());
                                    switch ((int)com.yiyiaddon.m.b.a<"sl3ufrs3vrnsv","mvwL1tmMQOyybKyQKv2pfO/7E31tp7n7Ao+v7w+4lFw=",6897818417828271306,-3972139266873534783,-5359610377996720899,-8164411773374744107>()) {
                                       case 85919078:
                                          continue;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           var2.setScreen(
                              new g(
                                 (String)com.yiyiaddon.m.b.a<"s6sh72l7ocoe1","KtiIMf8Q9dSpx5nUqPpng7V6pft7/4onDd32BKn7P/50Dasf",7041411427996802432,1053352069095051879,1501965107741782518,-107469699884170615>(),
                                 var0,
                                 List.copyOf(var4),
                                 () -> List.copyOf(var1.a().af()),
                                 var2x -> {
                                    if (!var5.contains(var2x)) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s2ocbicvz9niik","lkAqPZUDJzD8+Jk6jtCgrQO+2vh2VYP79k/YWckYQgA=",-3551229039511744633,-1912440113266945341,-1803245893778777137,867687362981009415>()) {
                                          case -1468062924:
                                             return;
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       ArrayList var3x = new ArrayList<>(var1.a().af());
                                       if (var3x.contains(var2x)) {
                                          switch ((int)com.yiyiaddon.m.b.a<"stimbysxrbb5w","1vOVurs8ieVllfTSaZHWcFOSMr3w4BV1pE2u6TjWXWs=",594934053652717821,4403331591825278796,-5013032160260511900,3852163132092580985>()) {
                                             case -1875284402:
                                                return;
                                             default:
                                                throw null;
                                          }
                                       } else {
                                          var3x.add(var2x);
                                          var1.a().i(var3x);
                                          var1.L();
                                       }
                                    }
                                 },
                                 var1x -> {
                                    ArrayList var2x = new ArrayList<>(var1.a().af());
                                    if (!var2x.remove(var1x)) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s27lamlkx2druj","cSw+qVMrAw9UA8x02Ef8gLOPUm6/3MT+oDnONdGxzAU=",-1088499541157689421,-4765574382658159538,6009170099127434166,-6207222249816474823>()) {
                                          case 1142975722:
                                             return;
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       var1.a().i(var2x);
                                       var1.L();
                                    }
                                 }
                              )
                           );
                           return;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s30h7tr1h6phpj","m/oHa+Po4Ehr2W66yVN56yAz7LMmv/SDRO+kELXNbRA=",5132745207545856464,8782818767923567927,-4648978145786867022,-5516029509680693407>()) {
                     case -93737186:
                        return;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      }
   }

   private static List<c.a> a(Registry<Enchantment> var0) {
      Set var1 = com.yiyiaddon.e.q.c.a.y();
      ArrayList var2 = new ArrayList();
      Iterator var3 = var0.registryKeySet().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s3jd28b2ru72hf","OEgeNYCzYBXMeU/Zhn+lzGeFZzQvaPd9sIyn5HN46ss=",7439537511124638869,6965866883836876073,5360967148008510164,1958186228051521421>()) {
         case 1645505786:
            while (var3.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s1121lazgsm2gu","/VrzSCvp/WxaLQnEIJcaQO60lh0m5Xf0hhBeFJie9nk=",5879570371273702373,-2864660634618155203,-2515029802383224225,-8377717297928539356>()) {
                  case -253333287:
                     ResourceKey var4 = (ResourceKey)var3.next();
                     Identifier var5 = var4.identifier();
                     if (!(String)com.yiyiaddon.m.b.a<"s21pttmlu2xfmt","WnZ1LK4/giuIYpkAHIO4ezpYU9jyzcaM135LDcjVLP0vq4SZVuwIZttLg2QqGg==",5598622779141708545,5695842180374711235,3031861084079355404,-3102665259979703039>()
                        .equals(var5.getNamespace())) {
                        switch ((int)com.yiyiaddon.m.b.a<"srg086dwe5w4s","KpQ//yoFZ67hJr69LVbV6btRi5I4deHTATYBJmSLmZg=",8238518292737172750,8389032016659698890,-7415837673881413430,9045213937779206268>()) {
                           case -1047780651:
                              switch ((int)com.yiyiaddon.m.b.a<"s1j3oo2hr9fahr","OD9NagsZDmAmar30oKaGJ0g5mjO0QJWfG4GA3hEd3Io=",-7377260971411576623,-1672956696783818317,-7010694467004818370,7767534246208864903>()) {
                                 case -326637596:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else if (!var1.contains(var5.getPath())) {
                        switch ((int)com.yiyiaddon.m.b.a<"svmhsxyz1dtpf","RsUL/L95gsblb+FSUTwH4p1LQqKt/iqzp5fM+OTXcjA=",-6393698707144982893,4852087975716631609,7703381882127772830,376882948484108493>()) {
                           case 295159886:
                              switch ((int)com.yiyiaddon.m.b.a<"s3aajk2ikiu64y","V5/DB9zvsZnu2mWNv7UbxeyzqqB1cVKFY/ETSayaDRY=",-9177405675596797607,5572597007588658406,7063258070532566322,-4594482290171305779>()) {
                                 case 58720796:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        var2.add(var4);
                        switch ((int)com.yiyiaddon.m.b.a<"s1yzl4rvv4iu6k","yMYDmxqQSKaaL0LRZYTopOzqRb/nAE6pAk+To4nvtH4=",4401877072260921827,160438991541271495,-1818653188107122372,5853769728007584841>()) {
                           case 689976466:
                              continue;
                           default:
                              throw null;
                        }
                     }
                  default:
                     throw null;
               }
            }

            ArrayList var11 = new ArrayList();
            HashSet var12 = new HashSet();
            Iterator var13 = ba.iterator();
            switch ((int)com.yiyiaddon.m.b.a<"sxllnwf53y33f","8XMlXrl13NiAbj+Zniuwuj05ocojIdft8SiJSh1itJ0=",-329647297521808554,7099765444808579316,-5818227928867501212,-8915228657676568746>()) {
               case -332679143:
                  while (var13.hasNext()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2l4qkewqq5u22","+wciCrMyuakc0cugric+o//tkDDB2kUmMNZEaMkb2zc=",-2565037771155117668,3307489561858639261,-3121809779364650463,4693683206594075393>()) {
                        case 635678507:
                           c.b var6 = (c.b)var13.next();
                           ArrayList var7 = new ArrayList();
                           Iterator var8 = var2.iterator();
                           switch ((int)com.yiyiaddon.m.b.a<"s39lu1hs259f2e","1KWrGun//E0KO6er6NhqjZIsYuYSapcnCxniUhy5Cog=",-2470418608253180597,-5868712882362353213,-2148053337764123411,6614824110064210614>()) {
                              case 769837613:
                                 while (var8.hasNext()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1ylal180m8m8g","OwMbvG55mJEiB0KIhxqBt16WIJlc2whjXI2K5PYdCV4=",-6846309463110532185,4633833731229955842,-4642808493431048853,8202328574357784678>()) {
                                       case -859874776:
                                          ResourceKey var9 = (ResourceKey)var8.next();
                                          String var10 = var9.identifier().getPath();
                                          if (var6.ai().contains(var10)) {
                                             switch ((int)com.yiyiaddon.m.b.a<"spkfruna23tmy","Xyj9oCKPpoiet4rq5nS7bwvmQVhpl6XqPd/B0ilU21k=",3179599489042996114,3607928587598923157,-9106918862509661652,-1041127238348133853>()) {
                                                case 1280635986:
                                                   if (!var12.add(var9.identifier().toString())) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s10axz5x0ngp2d","ou3ahdyUSqb3Dpbz83JR1/wFx/ccw6N5ScNEv+nDPE4=",-5623690064678875737,-6564041504894362325,-1878509140592438663,-4146915875097193348>()) {
                                                         case -541425046:
                                                            switch ((int)com.yiyiaddon.m.b.a<"s3f8y33yw0jfqt","RzUUVXhwAZ+FHBzA2kCeZeGOAMEUUA48LkEzIEQHxVs=",6499551409400337498,7905687598560756851,-1851841676413073211,1914928955788810569>()) {
                                                               case -1294425983:
                                                                  continue;
                                                               default:
                                                                  throw null;
                                                            }
                                                         default:
                                                            throw null;
                                                      }
                                                   } else {
                                                      var7.add(new c.a(var9.identifier().toString(), a(var0, var9), var6.D() + ""));
                                                      switch ((int)com.yiyiaddon.m.b.a<"s32kcauppt23vf","1VtKoiRCRgyDtRPWyB1zt/T4+08CpZF4tizSUn5J4eE=",-8573378821282620242,-3335981556861070023,3888997295557207356,-4095226352491102634>()) {
                                                         case 1842639530:
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

                                 var7.sort((var0x, var1x) -> c.compare(var0x.D(), var1x.D()));
                                 var11.addAll(var7);
                                 switch ((int)com.yiyiaddon.m.b.a<"s365lajho0h3xf","HTkVatoQwQkObqoKU3PsT9odokbjkS93wPleWZwvr2g=",7179649869175701802,1874355342995924863,2647373720241275492,-4538872481221698988>()) {
                                    case -1643454802:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  ArrayList var14 = new ArrayList();
                  Iterator var15 = var2.iterator();
                  switch ((int)com.yiyiaddon.m.b.a<"s1094b020z9egr","HDhK6KmkDIZOlzDVsn0YFKVMMMxTHZh8A5H9O/AfsL4=",8739950341419895944,1545301465614394534,3215292608006264562,211983219084263162>()) {
                     case -230197937:
                        while (var15.hasNext()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s32maq9tiy9wfi","APOtjunezDtRN/pDrdfLMEZjYTw86cSp4+73Scjfk/g=",-8273339247308704872,-4965895706882229941,-4144927328836931505,-7857433666218916556>()) {
                              case -1617340313:
                                 ResourceKey var16 = (ResourceKey)var15.next();
                                 if (!var12.add(var16.identifier().toString())) {
                                    switch ((int)com.yiyiaddon.m.b.a<"sp668dspc40s","tvkSJgfYJXrwOWbeE/gJbu1oezv9OdLcsR2wahdPNnE=",1155967952429814809,-7006204347583687839,-3975572840889362791,-6583882376340905562>()) {
                                       case 672995938:
                                          switch ((int)com.yiyiaddon.m.b.a<"s3m34hjhcje95t","wiGN30vxzPysM1FmvVWlWrG/IpS5VQdxfQgufvt1RLM=",3924765759263390269,-7654502176676974025,5398100328128873524,7683803827939715299>()) {
                                             case -1022068034:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    var14.add(
                                       new c.a(
                                          var16.identifier().toString(),
                                          a(var0, var16),
                                          (String)com.yiyiaddon.m.b.a<"s2ljhqms6g77f4","4UNIt9JNs9C3Vh1E+Fj6v973WVhx9oiJmfuAeIH4k6ev4NtMYgtrIvN3Uek=",3527626995064457126,-7046931058884234487,7978211636556192119,-4584161360385561482>()
                                       )
                                    );
                                    switch ((int)com.yiyiaddon.m.b.a<"s4r6w5hj89tp5","E9J0X6vIvy+qslM+RG+xAL/4o1x3T2xvMYxFfMHnbZo=",-6446253806375111038,7278930577776258201,-4562574141040905783,6806995383513559312>()) {
                                       case -1962280422:
                                          continue;
                                       default:
                                          throw null;
                                    }
                                 }
                              default:
                                 throw null;
                           }
                        }

                        var14.sort((var0x, var1x) -> c.compare(var0x.D(), var1x.D()));
                        var11.addAll(var14);
                        return var11;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         default:
            throw null;
      }
   }

   private static String a(Registry<Enchantment> var0, ResourceKey<Enchantment> var1) {
      return var0.get(var1).map(var0x -> ((Enchantment)var0x.value()).description().getString()).orElse(var1.identifier().toString());
   }

   private static final class a implements g.b {
      private final String mb;
      private final String mc;
      private final String md;
      private ItemStack g;

      private a(String var1, String var2, String var3) {
         this.mb = var1;
         this.mc = var2;
         this.md = var3;
      }

      @Override
      public String L() {
         return this.mb;
      }

      @Override
      public String D() {
         return this.mc;
      }

      @Override
      public String M() {
         return this.md;
      }

      @Override
      public boolean a(Canvas var1, float var2, float var3, float var4) {
         if (this.g == null) {
            switch ((int)com.yiyiaddon.m.b.a<"sa5ysimaqbmi","tg/GpD/s22ktsfxP3fNk7+oqDkJCuwkG8scDA5lMX00=",-2953013090964095999,8461817565113874631,8402484706307561023,8231656420036762280>()) {
               case 1304447897:
                  this.g = new ItemStack(Items.ENCHANTED_BOOK);
                  switch ((int)com.yiyiaddon.m.b.a<"s1i73pjsg6m1c9","ffSC00YDLxHKWrJmToROWr263LPCev+/6xCa/ap4jLE=",4302626842272242864,-3457962848085750694,5885176509539783540,3370150931455891585>()) {
                     case 181505736:
                        return com.yiyiaddon.l.g.c.a().a(var1, this.g, var2, var3, var4);
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            return com.yiyiaddon.l.g.c.a().a(var1, this.g, var2, var3, var4);
         }
      }

      @Override
      public ItemStack a() {
         return new ItemStack(Items.ENCHANTED_BOOK);
      }
   }

   private record b(String me, List<String> bb) {
      public String D() {
         return this.me;
      }

      public List<String> ai() {
         return this.bb;
      }
   }
}
