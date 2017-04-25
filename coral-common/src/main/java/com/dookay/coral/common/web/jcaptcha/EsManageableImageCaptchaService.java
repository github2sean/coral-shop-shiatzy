package com.dookay.coral.common.web.jcaptcha;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;

/**
 * 图形验证码处理
 * @since : 2016年11月17日
 * @author : kezhan
 * @version : v0.0.1
 */
public class EsManageableImageCaptchaService extends DefaultManageableImageCaptchaService {

	public EsManageableImageCaptchaService(
			com.octo.captcha.service.captchastore.CaptchaStore captchaStore,
			com.octo.captcha.engine.CaptchaEngine captchaEngine,
			int minGuarantedStorageDelayInSeconds, int maxCaptchaStoreSize,
			int captchaStoreLoadBeforeGarbageCollection) {
		super(
				captchaStore, captchaEngine, minGuarantedStorageDelayInSeconds, maxCaptchaStoreSize,
				captchaStoreLoadBeforeGarbageCollection);
	}

	public boolean hasCapcha(String id, String userCaptchaResponse) {
		return store.getCaptcha(id).validateResponse(userCaptchaResponse);
	}

	@Override
	public Boolean validateResponseForID(String ID, Object response)
			throws CaptchaServiceException {
		if (!this.store.hasCaptcha(ID)) {
			throw new CaptchaServiceException(
					"Invalid ID, could not validate unexisting or already validated captcha");
		}
		Boolean valid = this.store.getCaptcha(ID).validateResponse(response);
		//源码的这一句是没被注释的，这里我们注释掉，在下面暴露一个方法给我们自己来移除sessionId
		//this.store.removeCaptcha(ID);
		return valid;
	}

	public void removeCaptcha(String sessionId){
		if(sessionId!=null && this.store.hasCaptcha(sessionId)){
			this.store.removeCaptcha(sessionId);
		}
	}
}
