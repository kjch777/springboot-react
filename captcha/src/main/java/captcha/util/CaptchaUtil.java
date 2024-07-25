package captcha.util;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

// javax 로 import
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.captcha.Captcha;
import nl.captcha.backgrounds.GradiatedBackgroundProducer;
import nl.captcha.servlet.CaptchaServletUtil;
import nl.captcha.text.producer.NumbersAnswerProducer;
import nl.captcha.text.renderer.DefaultWordRenderer;

public class CaptchaUtil {

	public void getCaptchaImg(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		try {
			
			List<Font> fontList = new ArrayList<Font>();
			
			fontList.add(new Font("", Font.HANGING_BASELINE, 50));
			fontList.add(new Font("Courier", Font.CENTER_BASELINE, 50));
			fontList.add(new Font("", Font.PLAIN, 50));
			
			List<Color> colorList = new ArrayList<Color>();
			
			colorList.add(Color.BLACK);
			
			Captcha captcha = new Captcha.Builder(200, 60)
					          .addText(new NumbersAnswerProducer(5), new DefaultWordRenderer(colorList, fontList))
					          .addBackground(new GradiatedBackgroundProducer())
					          .gimp().addNoise().addNoise().addNoise().addBorder().build();
			
			res.setHeader("Cache-Control", "no-store");
			res.setHeader("Pragma", "no-cache");
			res.setDateHeader("Expires", 0);
			res.setContentType("image/jpeg");
			
			req.getSession().setAttribute(Captcha.NAME, captcha);
			CaptchaServletUtil.writeImage(res, captcha.getImage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
