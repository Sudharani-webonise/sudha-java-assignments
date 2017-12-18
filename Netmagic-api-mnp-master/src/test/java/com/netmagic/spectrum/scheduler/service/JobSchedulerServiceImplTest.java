package com.netmagic.spectrum.scheduler.service;

import java.sql.Timestamp;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netmagic.spectrum.dao.SofTransactionPaymentConfigurationDao;
import com.netmagic.spectrum.dao.UserTransactionDetailsDao;
import com.netmagic.spectrum.entity.SofTransactionPaymentConfigurationEntity;
import com.netmagic.spectrum.helpers.MockDaoData;
import com.netmagic.spectrum.service.OutstandingService;
import com.netmagic.spectrum.service.ShoppingCartService;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest({ JobSchedulerServiceImpl.class })
public class JobSchedulerServiceImplTest {

    @InjectMocks
    private JobSchedulerServiceImpl jobSchedulerServiceImplSpy;

    @Mock
    private SofTransactionPaymentConfigurationDao sofTransactionPaymentConfigurationDao;

    @Mock
    private UserTransactionDetailsDao userTransactionDao;

    @Mock
    private ShoppingCartService shoppingCartService;

    @Mock
    private OutstandingService outstandingService;

    @Test
    public void testCreatLineItemSofs() throws Exception {
        Whitebox.setInternalState(jobSchedulerServiceImplSpy, "mapper", new ObjectMapper());
        List<SofTransactionPaymentConfigurationEntity> configurationEntities = MockDaoData
                .findAllBySofTransactionStatus(sofTransactionPaymentConfigurationDao);
        configurationEntities.get(0)
                .setSofTransactionLastRetriveTime(PowerMockito.spy(new Timestamp(Mockito.anyLong())));
        jobSchedulerServiceImplSpy.creatLineItemSofs();
    }

    @Test
    public void tesCallToPeopleSoftAfterSofCreate() throws Exception {
        Whitebox.setInternalState(jobSchedulerServiceImplSpy, "mapper", new ObjectMapper());
        MockDaoData.findByUserTransationStatus(userTransactionDao);
        jobSchedulerServiceImplSpy.callToPeopleSoftAfterSofCreate();
    }

}
