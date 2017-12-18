package com.netmagic.spectrum.scheduler.service;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.netmagic.spectrum.exception.ServiceUnavailableException;

public interface JobSchedulerService {

    /**
     * This method also requests the pricing for updated price and requests to
     * create the sofs and update the sof transaction details table on the
     * responses. based on the sheduled time.
     * 
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @throws ServiceUnavailableException
     */

    public void creatLineItemSofs()
            throws JsonParseException, JsonMappingException, IOException, ServiceUnavailableException;

    /**
     * 
     * @throws ServiceUnavailableException
     */
    public void callToPeopleSoftAfterSofCreate() throws ServiceUnavailableException;
}
