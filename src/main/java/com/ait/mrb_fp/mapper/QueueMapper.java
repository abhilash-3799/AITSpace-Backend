package com.ait.mrb_fp.mapper;

import com.ait.mrb_fp.dto.request.QueueRequestDTO;
import com.ait.mrb_fp.dto.response.QueueResponseDTO;
import com.ait.mrb_fp.entity.*;

public class QueueMapper {

    private QueueMapper() {
    }

    public static Queue toEntity(QueueRequestDTO r, Office office) {
        Queue q = new Queue();
        q.setOffice(office);
        q.setQueueName(r.getQueueName());
        q.setTotalSeats(r.getTotalSeats());
        q.setActive(true);
        return q;
    }

    public static QueueResponseDTO toResponse(Queue q) {
        QueueResponseDTO r = new QueueResponseDTO();
        r.setQueueId(q.getQueueId());
        r.setQueueName(q.getQueueName());
        r.setOfficeName(q.getOffice() != null ? q.getOffice().getOfficeName() : null);
        r.setTotalSeats(q.getTotalSeats());
        r.setActive(q.isActive());
        return r;
    }

    public static void updateEntity(Queue q, QueueRequestDTO r, Office office) {
        q.setOffice(office);
        q.setQueueName(r.getQueueName());
        q.setTotalSeats(r.getTotalSeats());
    }
}
